package com.ramiro.poclayoutcomprovante.service;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.stereotype.Service;

import com.ramiro.poclayoutcomprovante.generated.ComprovLexer;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser;
import com.ramiro.poclayoutcomprovante.model.ResultadoVisitor;

@Service
public class ServiceBind {

	public String bind(String padrao, Object object) {

		ResultadoVisitor resultado = this.tratar(padrao, object);

		if (resultado.Sucesso()) {
			return resultado.getResultado();
		}

		return padrao;

	}

	private ResultadoVisitor tratar(String padrao, Object object) {
		
		ComprovanteVisitorError error = new ComprovanteVisitorError();
		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(padrao));
		lexer.removeErrorListeners();
		lexer.addErrorListener(error);

		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(error);

		try {

			ParseTree tree = parser.programa();
			ComprovanteVisitor visitor = new ComprovanteVisitor(object);

			String retorno = visitor.visit(tree).asString();

			return ResultadoVisitor.comStatus(true).comResultado(retorno);

		} catch (ParseCancellationException e) {

			return ResultadoVisitor.comStatus(false).comMensagemDeErro(e.getMessage());

		}
	}
}
