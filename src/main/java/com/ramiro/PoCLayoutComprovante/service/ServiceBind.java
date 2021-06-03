package com.ramiro.poclayoutcomprovante.service;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.stereotype.Service;

import com.ramiro.poclayoutcomprovante.generated.ComprovLexer;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser;


@Service
public class ServiceBind {

		public String bind(String padrao, Object object) {
			
			System.out.println(padrao);
			
			ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(padrao));
	        CommonTokenStream tokens = new CommonTokenStream(lexer);
	        ComprovParser parser = new ComprovParser(tokens);
	        ParseTree tree = parser.programa();
	        ComprovanteVisitor visitor = new ComprovanteVisitor(object, parser);
	        
	        return visitor.visit(tree).asString();
		
		}
}
