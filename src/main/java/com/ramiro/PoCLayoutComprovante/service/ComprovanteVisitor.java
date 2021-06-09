package com.ramiro.poclayoutcomprovante.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.apache.commons.text.WordUtils;

import com.google.gson.GsonBuilder;

import com.jayway.jsonpath.JsonPath;

import com.ramiro.poclayoutcomprovante.generated.ComprovBaseVisitor;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.AbbreviateContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.AndExprContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.BooleanoContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.CapitalizeContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.ComparativoContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.ConcatenarContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.ContainsContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.FormatCurrencyContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.IfContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.InitialsContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.JsonContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.NumeroContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.OrExprContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.ParentesesContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.RelacionalContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.StringContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.ToLowerCaseContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.ToUpperCaseContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.UncapitalizeContext;
import com.ramiro.poclayoutcomprovante.model.Value;

public class ComprovanteVisitor extends ComprovBaseVisitor<Value> {

	private final String json;
	
	public ComprovanteVisitor(Object object) {
		
		if(object == null)
			throw new IllegalArgumentException("object is null!");

		this.json = new GsonBuilder().create().toJson(object);
	}
	
	private String obterValorJson(String padrao) {
				
		if(padrao == null || (!padrao.contains("$")))
			return padrao;
		
		try {
					
			Object obj = JsonPath.read(this.json, padrao);

			if (obj instanceof List<?>)
				return ((List<?>) obj).stream().findFirst().isPresent()
						? ((List<?>) obj).stream().findFirst().get().toString()
						: "";

			return obj == null ? "" : obj.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return padrao;
		}
	}

	@Override
	public Value visitJson(JsonContext ctx) {
		return new Value(this.obterValorJson(this.visit(ctx.expressao()).asString()));
	}

	@Override
	public Value visitConcatenar(ConcatenarContext ctx) {
		Value left = this.visit(ctx.expressao(0));
		Value right = this.visit(ctx.expressao(1));
		if (left.isDecimal() && right.isDecimal())
			return new Value(left.asDecimal().add(right.asDecimal()));
		return new Value(left.asString() + right.asString());

	}

	@Override
	public Value visitNumero(NumeroContext ctx) {
		return new Value(new BigDecimal(ctx.getText()));
	}

	@Override
	public Value visitString(StringContext ctx) {
		String str = ctx.getText();
		str = str.substring(1, str.length() - 1).replace("\"\"", "\"");
		return new Value(str);
	}

	@Override
	public Value visitBooleano(BooleanoContext ctx) {
		return new Value(Boolean.valueOf(ctx.getText()));
	}

	@Override
	public Value visitParenteses(ParentesesContext ctx) {
		return this.visit(ctx.expressao());

	}

	@Override
	public Value visitComparativo(ComparativoContext ctx) {
		Value left = this.visit(ctx.expressao(0));
		Value right = this.visit(ctx.expressao(1));

		switch (ctx.op.getType()) {
		case ComprovParser.IGUAL:
			return left.isDecimal() && right.isDecimal() ? new Value(Boolean.valueOf(left.asDecimal().compareTo(right.asDecimal()) == 0))
					: new Value(Boolean.valueOf(left.equals(right)));

		case ComprovParser.DIFERENTE:
			return left.isDecimal() && right.isDecimal() ? new Value(Boolean.valueOf(left.asDecimal().compareTo(right.asDecimal()) != 0))
					: new Value(Boolean.valueOf(!left.equals(right)));

		default:
			throw new RuntimeException("operador desconhecido: " + ctx.op.getText());
		}
	}

	@Override
	public Value visitRelacional(RelacionalContext ctx) {
		Value left = this.visit(ctx.expressao(0));
		Value right = this.visit(ctx.expressao(1));

		if (!left.isDecimal() || !right.isDecimal())
			return Value.VOID;

		switch (ctx.op.getType()) {
		case ComprovParser.MAIOR:
			return new Value(Boolean.valueOf(left.asDecimal().compareTo(right.asDecimal()) == 1));

		case ComprovParser.MAIORIGUAL:
			return new Value(Boolean.valueOf(left.asDecimal().compareTo(right.asDecimal()) >= 0));

		case ComprovParser.MENOR:
			return new Value(Boolean.valueOf(left.asDecimal().compareTo(right.asDecimal()) == -1));

		case ComprovParser.MENORIGUAL:
			return new Value(Boolean.valueOf(left.asDecimal().compareTo(right.asDecimal()) <= 0));

		default:
			throw new RuntimeException("operador desconhecido: " + ctx.op.getText());
		}

	}

	@Override
	public Value visitAndExpr(AndExprContext ctx) {
		if(!this.visit(ctx.expressao(0)).asBoolean())
			return new Value(Boolean.FALSE);
		
		if(!this.visit(ctx.expressao(1)).asBoolean())
			return new Value(Boolean.FALSE);

		return new Value(Boolean.TRUE);
	}

	@Override
	public Value visitOrExpr(OrExprContext ctx) {
		
		if(this.visit(ctx.expressao(0)).asBoolean())
			return new Value(Boolean.TRUE);
		
		if(this.visit(ctx.expressao(1)).asBoolean())
			return new Value(Boolean.TRUE);

		return new Value(Boolean.FALSE);
	}

	@Override
	public Value visitCapitalize(CapitalizeContext ctx) {
		return new Value(WordUtils.capitalize(this.visit(ctx.expressao()).asString()));
	}
	
	@Override
	public Value visitUncapitalize(UncapitalizeContext ctx) {
		return new Value(WordUtils.uncapitalize(this.visit(ctx.expressao()).asString()));
	}
	
	@Override
	public Value visitToUpperCase(ToUpperCaseContext ctx) {
		return new Value(this.visit(ctx.expressao()).asString().toUpperCase());
	}
	
	@Override
	public Value visitToLowerCase(ToLowerCaseContext ctx) {
		return new Value(this.visit(ctx.expressao()).asString().toLowerCase());
	}
	
	@Override
	public Value visitInitials(InitialsContext ctx) {
		char[] chars = {' ', '.'};
		return new Value(WordUtils.initials(this.visit(ctx.expressao()).asString(), chars));
	}

	@Override
	public Value visitAbbreviate(AbbreviateContext ctx) {
		
		String texto = this.visit(ctx.str).asString();
		int lower = this.visit(ctx.lower).asDecimal().intValue();
		int upper = this.visit(ctx.upper).asDecimal().intValue();

		return new Value(WordUtils.abbreviate(texto, lower, upper, ""));
	}
	@Override
	public Value visitContains(ContainsContext ctx) {
		
		String texto = this.visit(ctx.str).asString();
		String sequence = this.visit(ctx.sequence).asString();

		return new Value(Boolean.valueOf(texto.contains(sequence)));
		
	}
	
	@Override
	public Value visitFormatCurrency(FormatCurrencyContext ctx) {

		Value value = this.visit(ctx.value);
		if(!value.isDecimal())
			throw new RuntimeException("valor deve ser numero/decimal :: " + value.asString());
		
		String language = this.visit(ctx.language).asString();
		String country = this.visit(ctx.country).asString();
		
		Locale locale = new Locale(language, country); 
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
		
		return new Value(currencyFormatter.format(value.asDecimal()));
		
	}

	@Override
	public Value visitIf(IfContext ctx) {

		Value resultado = this.visit(ctx.expressao(0));
		
		if(!resultado.isBoolean())
			throw new RuntimeException("expressao deve retornar um boolean :: " + resultado.asString());

		if (resultado.asBoolean().booleanValue()) {
			return this.visit(ctx.expressao(1));
		}
		return this.visit(ctx.expressao(2));
	}

}