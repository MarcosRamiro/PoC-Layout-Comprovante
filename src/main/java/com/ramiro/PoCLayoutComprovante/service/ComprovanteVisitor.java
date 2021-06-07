package com.ramiro.poclayoutcomprovante.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.text.WordUtils;

import com.google.gson.GsonBuilder;

import com.jayway.jsonpath.JsonPath;

import com.ramiro.poclayoutcomprovante.generated.ComprovBaseVisitor;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.AndExprContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.BooleanoContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.CapitalizeContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.ComparativoContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.ConcatenarContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.IfContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.JsonContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.NumeroContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.OrExprContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.ParentesesContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.RelacionalContext;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser.StringContext;
import com.ramiro.poclayoutcomprovante.model.Value;

public class ComprovanteVisitor extends ComprovBaseVisitor<Value> {

	private final String json;
	
	public ComprovanteVisitor(Object object) {
		
		if(object == null)
			throw new IllegalArgumentException("object is null!");

		this.json = new GsonBuilder().create().toJson(object);
	}
	
	private String obterValorJson(String padrao) {
		
		System.out.println("Entrou json:: " + padrao);
		
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
			return left.isDecimal() && right.isDecimal() ? new Value(left.asDecimal().compareTo(right.asDecimal()) == 0)
					: new Value(left.equals(right));

		case ComprovParser.DIFERENTE:
			return left.isDecimal() && right.isDecimal() ? new Value(left.asDecimal().compareTo(right.asDecimal()) != 0)
					: new Value(!left.equals(right));

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
			return new Value(left.asDecimal().compareTo(right.asDecimal()) == 1);

		case ComprovParser.MAIORIGUAL:
			return new Value(left.asDecimal().compareTo(right.asDecimal()) >= 0);

		case ComprovParser.MENOR:
			return new Value(left.asDecimal().compareTo(right.asDecimal()) == -1);

		case ComprovParser.MENORIGUAL:
			return new Value(left.asDecimal().compareTo(right.asDecimal()) <= 0);

		default:
			throw new RuntimeException("operador desconhecido: " + ctx.op.getText());
		}

	}

	@Override
	public Value visitAndExpr(AndExprContext ctx) {
		Value left = this.visit(ctx.expressao(0));
		Value right = this.visit(ctx.expressao(1));

		return new Value(left.asBoolean() && right.asBoolean());
	}

	@Override
	public Value visitOrExpr(OrExprContext ctx) {
		Value left = this.visit(ctx.expressao(0));
		Value right = this.visit(ctx.expressao(1));

		return new Value(left.asBoolean() || right.asBoolean());
	}

	@Override
	public Value visitCapitalize(CapitalizeContext ctx) {
		System.out.println("Entrou capitalize");
		return new Value(WordUtils.capitalize(this.visit(ctx.expressao()).asString().toLowerCase()));
	}

	@Override
	public Value visitIf(IfContext ctx) {

		Value resultado = this.visit(ctx.expressao(0));

		if (resultado.asBoolean().booleanValue()) {
			return new Value(this.visit(ctx.expressao(1)));
		}
		return new Value(this.visit(ctx.expressao(2)));
	}

}