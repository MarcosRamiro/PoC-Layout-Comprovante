package com.ramiro.poclayoutcomprovante.service;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.stereotype.Service;

import com.ramiro.poclayoutcomprovante.generated.ComprovLexer;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser;
import com.ramiro.poclayoutcomprovante.service.visitor.ComprovanteVisitor;
import com.ramiro.poclayoutcomprovante.service.visitor.ComprovanteVisitorErrorListener;

@Service
public class ServiceBind {

	public String bind(String padrao, Object object) throws ServiceBindException {

		try {
			return this.tratar(padrao, object);
		} catch (ParseCancellationException e) {
			throw new ServiceBindException("Erro ao tentar tratar o tratar o padrao: " + padrao, e);
		}

	}

	private String tratar(String padrao, Object object) {

		ComprovanteVisitorErrorListener error = new ComprovanteVisitorErrorListener();
		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(padrao));
		lexer.removeErrorListeners();
		lexer.addErrorListener(error);

		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(error);

		ParseTree tree = parser.programa();
		ComprovanteVisitor visitor = new ComprovanteVisitor(object);

		return visitor.visit(tree).asString();

	}
}
