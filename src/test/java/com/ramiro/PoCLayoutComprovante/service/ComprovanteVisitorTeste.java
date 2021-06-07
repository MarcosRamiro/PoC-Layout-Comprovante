package com.ramiro.poclayoutcomprovante.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.BitSet;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTree;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;

import com.ramiro.poclayoutcomprovante.generated.ComprovLexer;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ComprovanteVisitorTeste {

	@Test
	public void deveCompararDoisInteriosIguais() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" 22 == 22 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "true");

	}

	@Test
	public void deveCompararESomarJuntosComParentese() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" 23 > (11 + 0005) "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "true");

	}

	@Test
	public void deveCompararDoisInteriosDiferente_Igual() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" 22 == 21 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "false");

	}

	@Test
	public void deveCompararDoisInteriosIguais_Diferente() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" 21 != 21 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "false");

	}

	@Test
	public void deveCompararDoisInteriosDiferentes_Diferente() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" 22 != 21 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "true");

	}

	@Test
	public void deveCompararDuasStringsIguais_Igual() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" \"aqui é um teste\" == \"aqui é um teste\" "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "true");

	}

	@Test
	public void deveCompararDuasStringsDiferentes_Igual() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" \"aqui é um teste\" == \"texto diferente\" "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "false");

	}

	@Test
	public void deveCompararDuasStringsDiferentes_Diferente() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" \"aqui é um teste\" != \"texto diferente\" "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "true");

	}

	@Test
	public void deveCompararDuasStringsIguais_Diferente() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" \"aqui é um teste\" != \"aqui é um teste\" "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "false");

	}

	@Test
	public void deveCompararNumeroMenor_Menor() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" 1 < 2 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "true");

	}

	@Test
	public void deveCompararNumeroMaior_Menor() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" 2 < 1 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "false");

	}

	@Test
	public void deveCompararNumeroMaior_MaiorIgual() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" 2 >= 1 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "true");

	}

	@Test
	public void deveCompararNumeroIgual_MaiorIgual() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" 2 >= 2 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "true");

	}

	@Test
	public void deveCompararNumeroMenor_MaiorIgual() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" 1 >= 2 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "false");

	}

	@Test
	public void deveCompararNumeroMenor_MenorIgual() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" 1 <= 2 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "true");

	}

	@Test
	public void deveCompararNumerosguais_MenorIgual() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" 2 <= 2 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "true");

	}

	@Test
	public void deveCompararNumeroMaior_MenorIgual() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" 3 <= 2 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "false");

	}

	@Test
	public void deveCompararDuasAfirmacoesSaoVerdadeiras_And() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" 2 == 2 && 9 > 5 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "true");

	}

	@Test
	public void deveCompararApenasUmaAfirmacaoEhVerdadeira_And() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" 2 != 2 && 9 > 5 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "false");

	}

	@Test
	public void deveCompararQuandoAsDuasfirmacaoSaoFalsas_And() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" 2 != 2 && 9 < 5 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "false");

	}

	@Test
	public void deveCompararQuandoUmaAfirmacaoEhVerdadeira_Or() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" 2 > 2 || 9 == 9 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "true");

	}

	@Test
	public void deveCompararQuandoAsDuasAfirmacaoSaoVerdadeiras_Or() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" 2 == 2 || 9 == 9 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "true");

	}

	@Test
	public void deveCompararQuandoAsDuasAfirmacaoSaoFalsas_Or() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" 2 == 1 || 8 == 9 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "false");

	}

	@Test
	public void deveConcatenarDuasStrings_Concatenar() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" \"Meu nome é \" + \"João\" "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "Meu nome é João");

	}

	@Test
	public void deveConcatenarStringENumero_Concatenar() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" \"O valor total foi \" + 2.2 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "O valor total foi 2.2");

	}

	@Test
	public void deveSomarDoisNumero_Concatenar() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" 4.78 + 2.2 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "6.98");

	}

	@Test
	public void deveRetornarUmNumero() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" 4.78 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "4.78");

	}

	@Test
	public void deveRetornarUmaString() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" \"Meu nome é João da Silva\" "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "Meu nome é João da Silva");

	}

	@Test
	public void deveRetornarUmBooleanTrue() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" true "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "true");
		assertEquals(visitor.visit(tree).asBoolean(), new Boolean(true));

	}

	@Test
	public void deveRetornarUmBooleanFalse() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" false "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "false");
		assertEquals(visitor.visit(tree).asBoolean(), new Boolean(false));

	}

	@Test
	public void deveTratarParenteses_Boolean() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" ( 1 > 2 || 45 != 45  )  == true "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "false");
		assertEquals(visitor.visit(tree).asBoolean(), new Boolean(false));

	}

	@Test
	public void deveTratarParenteses_Numero() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" ( 4 + 5  )  == 9 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "true");
		assertEquals(visitor.visit(tree).asBoolean(), new Boolean(true));

	}

	@Test
	public void deveTratarParenteses_somaNumeros() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" ( 4 + 5  )  + 2 "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "11");

	}

	@Test
	public void deveRetornarPrimeirasLetrasEmMaiuscula_Capitalize() {

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" capitalize { \"meu nome é joãozinho\" } "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "Meu Nome É Joãozinho");

	}

	@Test
	public void deveRetornarConcatenarFuncaoCapitalizeEString_Concatenar() {

		ComprovLexer lexer = new ComprovLexer(
				CharStreams.fromString(" capitalize { \"meu nome é joãozinho\" } + \"!!\" "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(new Object());

		assertEquals(visitor.visit(tree).asString(), "Meu Nome É Joãozinho!!");

	}

	@Test
	public void deveObterValorAPartirDeJsonPath_Json() {

		Cliente marcos = new Cliente();
		marcos.setNome("marcos");
		marcos.setIdade("31");

		Cliente jose = new Cliente();
		jose.setNome("jose");
		jose.setIdade("45");

		marcos.setCliente(jose);

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" json { \"$.cliente.nome\" } "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(marcos);

		assertEquals(visitor.visit(tree).asString(), "jose");

	}

	@Test
	public void deveCombinarFuncoesJsonECaptalize() {

		Cliente marcos = new Cliente();
		marcos.setNome("marcos");
		marcos.setIdade("31");

		Cliente jose = new Cliente();
		jose.setNome("jose");
		jose.setIdade("45");

		marcos.setCliente(jose);

		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" capitalize { json { \"$.cliente.nome\" }  } "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(marcos);

		assertEquals(visitor.visit(tree).asString(), "Jose");

	}

	@Test
	public void deveTratarIfVerdadeiro_If() {

		Cliente marcos = new Cliente();
		marcos.setNome("marcos");
		marcos.setIdade("31");

		Cliente jose = new Cliente();
		jose.setNome("jose");
		jose.setIdade("45");

		marcos.setCliente(jose);

		ComprovLexer lexer = new ComprovLexer(CharStreams
				.fromString(" capitalize { json { \"$.cliente.nome\" }  } == \"Jose\" ? \"verdadeiro\" : \"falso\" "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa();

		ComprovanteVisitor visitor = new ComprovanteVisitor(marcos);

		assertEquals(visitor.visit(tree).asString(), "verdadeiro");

	}

	@Test
	public void deveTratarIfFalso_If() {

		Cliente marcos = new Cliente();
		marcos.setNome("marcos");
		marcos.setIdade("31");

		Cliente jose = new Cliente();
		jose.setNome("jose");
		jose.setIdade("45");

		marcos.setCliente(jose);

		ComprovLexer lexer = new ComprovLexer(CharStreams
				.fromString(" capitalize { json { \"$.cliente.nome\" }  } == \"Maria\" ? \"verdadeiro\" : \"falso\" "));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		ParseTree tree = parser.programa(); // parse

		ComprovanteVisitor visitor = new ComprovanteVisitor(marcos);

		assertEquals(visitor.visit(tree).asString(), "falso");

	}

	@Test
	public void deveGerarErro_FuncaoNaoExiste() {

		Cliente marcos = new Cliente();
		marcos.setNome("marcos");
		marcos.setIdade("31");

		Cliente jose = new Cliente();
		jose.setNome("jose");
		jose.setIdade("45");

		marcos.setCliente(jose);

		ComprovanteVisitorError error = new ComprovanteVisitorError();
		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(" jsonNNN { \"$.nome\" } == \"marcos\" "));
		lexer.removeErrorListeners();
		lexer.addErrorListener(error);
		CommonTokenStream tokens = new CommonTokenStream(lexer);

		ComprovParser parser = new ComprovParser(tokens);

		ParseTree tree = parser.programa(); // parse

		System.out.println("tree.toStringTree :: " + tree.toStringTree(parser));
		ComprovanteVisitor visitor = new ComprovanteVisitor(marcos);

		String retorno = visitor.visit(tree).asString();

		System.out.println("retorno:: " + retorno);

		System.out.println("parser.getBuildParseTree :: " + parser.getBuildParseTree());

		assertEquals(retorno, "true");

	}
}
