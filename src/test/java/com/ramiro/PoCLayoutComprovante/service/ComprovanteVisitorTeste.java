package com.ramiro.poclayoutcomprovante.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import com.ramiro.poclayoutcomprovante.generated.ComprovLexer;
import com.ramiro.poclayoutcomprovante.generated.ComprovParser;
import com.ramiro.poclayoutcomprovante.model.Value;


public class ComprovanteVisitorTeste {
	
	private static Cliente getCliente() {
		Cliente cliente = new Cliente();
		cliente.setNome("MARCOS");
		cliente.setIdade("31");
		Cliente cliente2 = new Cliente();
		cliente2.setNome("jose");
		cliente2.setIdade("45");
		cliente.setCliente(cliente2);
		return cliente;
	}
	
	private static Value chamarVisitor(String padrao, Object object) {
		
		ComprovLexer lexer = new ComprovLexer(CharStreams.fromString(padrao));
		ComprovanteVisitorError error = new ComprovanteVisitorError();
		lexer.removeErrorListeners();
		lexer.addErrorListener(error);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ComprovParser parser = new ComprovParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(error);
		ParseTree tree = parser.programa();
		ComprovanteVisitor visitor = new ComprovanteVisitor(object);
		return visitor.visit(tree);
	}

	@Test
	public void deveCompararDoisInteriosIguais() {
		
		String padrao = " 22 == 22 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("true", value.asString());
		assertTrue(value.asBoolean());

	}

	@Test
	public void deveCompararESomarJuntosComParentese() {

		String padrao = " 23 > (11 + 0005)";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("true", value.asString());
		assertTrue(value.asBoolean());

	}

	@Test
	public void deveCompararDoisInteriosDiferente_Igual() {
		
		String padrao = " 22 == 21 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("false", value.asString());
		assertFalse(value.asBoolean());


	}

	@Test
	public void deveCompararDoisInteriosIguais_Diferente() {

		String padrao = " 21 != 21 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("false", value.asString());
		assertFalse(value.asBoolean());

	}

	@Test
	public void deveCompararDoisInteriosDiferentes_Diferente() {

		String padrao = " 22 != 21 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("true", value.asString());
		assertTrue(value.asBoolean());

	}

	@Test
	public void deveCompararDuasStringsIguais_Igual() {

		String padrao = " \"aqui é um teste\" == \"aqui é um teste\" ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("true", value.asString());
		assertTrue(value.asBoolean());

	}

	@Test
	public void deveCompararDuasStringsDiferentes_Igual() {

		String padrao = " \"aqui é um teste\" == \"texto diferente\" ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("false", value.asString());
		assertFalse(value.asBoolean());

	}

	@Test
	public void deveCompararDuasStringsDiferentes_Diferente() {

		String padrao = " \"aqui é um teste\" != \"texto diferente\" ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("true", value.asString());
		assertTrue(value.asBoolean());

	}

	@Test
	public void deveCompararDuasStringsIguais_Diferente() {

		String padrao = " \"aqui é um teste\" != \"aqui é um teste\" ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("false", value.asString());
		assertFalse(value.asBoolean());

	}

	@Test
	public void deveCompararNumeroMenor_Menor() {

		String padrao = " 1 < 2 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("true", value.asString());
		assertTrue(value.asBoolean());

	}

	@Test
	public void deveCompararNumeroMaior_Menor() {

		String padrao = " 2 < 1 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("false", value.asString());
		assertFalse(value.asBoolean());

	}

	@Test
	public void deveCompararNumeroMaior_MaiorIgual() {

		String padrao = " 2 >= 1 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("true", value.asString());
		assertTrue(value.asBoolean());

	}

	@Test
	public void deveCompararNumeroIgual_MaiorIgual() {

		String padrao = " 2 >= 2 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("true", value.asString());
		assertTrue(value.asBoolean());

	}

	@Test
	public void deveCompararNumeroMenor_MaiorIgual() {

		String padrao = " 1 >= 2 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("false", value.asString());
		assertFalse(value.asBoolean());
	}

	@Test
	public void deveCompararNumeroMenor_MenorIgual() {

		String padrao = " 1 <= 2 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("true", value.asString());
		assertTrue(value.asBoolean());

	}

	@Test
	public void deveCompararNumerosguais_MenorIgual() {

		String padrao = " 2 <= 2 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("true", value.asString());
		assertTrue(value.asBoolean());

	}

	@Test
	public void deveCompararNumeroMaior_MenorIgual() {

		String padrao = " 3 <= 2 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("false", value.asString());
		assertFalse(value.asBoolean());

	}

	@Test
	public void deveCompararDuasAfirmacoesSaoVerdadeiras_And() {

		String padrao = " 2 == 2 && 9 > 5 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("true", value.asString());
		assertTrue(value.asBoolean());

	}

	@Test
	public void deveCompararApenasUmaAfirmacaoEhVerdadeira_And() {

		String padrao = " 2 != 2 && 9 > 5 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("false", value.asString());
		assertFalse(value.asBoolean());

	}

	@Test
	public void deveCompararQuandoAsDuasfirmacaoSaoFalsas_And() {

		String padrao = " 2 != 2 && 9 < 5 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("false", value.asString());
		assertFalse(value.asBoolean());

	}

	@Test
	public void deveCompararQuandoUmaAfirmacaoEhVerdadeira_Or() {

		String padrao = " 2 > 2 || 9 == 9 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("true", value.asString());
		assertTrue(value.asBoolean());

	}

	@Test
	public void deveCompararQuandoAsDuasAfirmacaoSaoVerdadeiras_Or() {

		String padrao = " 2 == 2 || 9 == 9 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("true", value.asString());
		assertTrue(value.asBoolean());

	}

	@Test
	public void deveCompararQuandoAsDuasAfirmacaoSaoFalsas_Or() {

		String padrao = " 2 == 1 || 8 == 9 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("false", value.asString());
		assertFalse(value.asBoolean());

	}

	@Test
	public void deveConcatenarDuasStrings_Concatenar() {

		String padrao = " \"Meu nome é \" + \"João\" ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("Meu nome é João", value.asString());

	}

	@Test
	public void deveConcatenarStringENumero_Concatenar() {

		String padrao = " \"O valor total foi \" + 2.2 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("O valor total foi 2.2", value.asString());

	}

	@Test
	public void deveSomarDoisNumero_Concatenar() {
		
		String padrao = " 4.78 + 2.2 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("6.98", value.asString());
		assertTrue(new BigDecimal("6.98").equals(value.asDecimal()));

	}

	@Test
	public void deveRetornarUmNumero() {

		String padrao = " 4.78 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("4.78", value.asString());
		assertTrue(new BigDecimal("4.78").equals(value.asDecimal()));

	}

	@Test
	public void deveRetornarUmaString() {
	
		String padrao = " \"Meu nome é João da Silva\" ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("Meu nome é João da Silva", value.asString());

	}

	@Test
	public void deveRetornarUmBooleanTrue() {

		String padrao = " true ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("true", value.asString());
		assertTrue(value.asBoolean());

	}

	@Test
	public void deveRetornarUmBooleanFalse() {

		String padrao = " false ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("false", value.asString());
		assertFalse(value.asBoolean());

	}

	@Test
	public void deveTratarParenteses_Boolean() {

		String padrao = " ( 1 > 2 || 45 != 45  )  == true ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("false", value.asString());
		assertFalse(value.asBoolean());

	}
	
	@Test
	public void deveTratarParentesesVerdadeiro_Boolean() {

		String padrao = " ( 12 > 2 && 45 == 45  )  == true ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("true", value.asString());
		assertTrue(value.asBoolean());

	}

	@Test
	public void deveTratarParenteses_Numero() {

		String padrao = " ( 4 + 5  )  == 9 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("true", value.asString());
		assertTrue(value.asBoolean());

	}

	@Test
	public void deveTratarParenteses_somaNumeros() {

		String padrao = " ( 4 + 5  )  + 2 ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("11", value.asString());

	}

	@Test
	public void deveRetornarPrimeirasLetrasEmMaiuscula_Capitalize() {

		String padrao = " capitalize { \"MEU nome é joãozinho\" } ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("MEU Nome É Joãozinho", value.asString());

	}
	
	@Test
	public void deveRetornarPrimeirasLetrasEmMinuscula_Uncapitalize() {

		String padrao = " uncapitalize { \"MEU nOmE NÃO É jOHnnY\" } ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("mEU nOmE nÃO é jOHnnY", value.asString());

	}

	@Test
	public void deveRetornarConcatenarFuncaoCapitalizeEString_Concatenar() {

		String padrao = " capitalize { \"MEU noMe é joãozinho\" } + \"!!\" ";
		Value value = chamarVisitor(padrao, new Object());
		assertEquals("MEU NoMe É Joãozinho!!", value.asString());

	}
	
	@Test
	public void deveObterValorAPartirDeJsonPath_Json() {
		
		String padrao = " json { \"$.cliente.nome\" } ";
		Value value = chamarVisitor(padrao, getCliente());
		assertEquals("jose", value.asString());

	}

	@Test
	public void deveCombinarFuncoesJsonECaptalize() {
		
		String padrao = " capitalize { json { \"$.cliente.nome\" }  } ";
		Value value = chamarVisitor(padrao, getCliente());
		assertEquals("Jose", value.asString());

	}
	
	

	@Test
	public void deveTratarIfVerdadeiro_If() {

		String padrao = " capitalize { json { \"$.cliente.nome\" }  } == \"Jose\" ? \"verdadeiro\" : \"falso\" ";
		Value value = chamarVisitor(padrao, getCliente());
		assertEquals("verdadeiro", value.asString());

	}

	@Test
	public void deveTratarIfFalso_If() {

		String padrao = " capitalize { json { \"$.cliente.nome\" }  } == \"Maria\" ? \"verdadeiro\" : \"falso\" ";
		Value value = chamarVisitor(padrao, getCliente());
		assertEquals("falso", value.asString());

	}
	
	@Test
	public void deveTratarStringsParaUpperCase_toUpperCase() {

		String padrao = " touppercase  { \"mEU NOmE NÃO É jOHnnY\" } ";
		Value value = chamarVisitor(padrao, getCliente());
		assertEquals("MEU NOME NÃO É JOHNNY", value.asString());

	}
	
	@Test
	public void deveTratarStringsParaLowerCase_toLowerCase() {

		String padrao = " tolowercase  { \"mEU NOmE NÃO É jOHnnY\" } ";
		Value value = chamarVisitor(padrao, getCliente());
		assertEquals("meu nome não é johnny", value.asString());

	}
	
	@Test
	public void deveTratarStringsParaLowerCaseECapitalize() {

		String padrao = " capitalize {  tolowercase  { \"mEU NOmE NÃO É jOHnnY\" } } ";
		Value value = chamarVisitor(padrao, getCliente());
		assertEquals("Meu Nome Não É Johnny", value.asString());

	}
	
	@Test
	public void deveTratarStringsParaLowerCaseECapitalizeEJson() {

		String padrao = " capitalize {  tolowercase  { json { \"$.nome\" } } } + \" Ramiro\" ";
		Value value = chamarVisitor(padrao, getCliente());
		assertEquals("Marcos Ramiro", value.asString());

	}
	
	@Test
	public void deveObterAsIniciaisDeUmaStringComPonto() {

		String padrao = " initials {  \"Ben J.Lee\" } ";
		Value value = chamarVisitor(padrao, getCliente());
		assertEquals("BJL", value.asString());

	}
	
	@Test
	public void deveObterAsIniciaisDeUmaStringSemPontoMaiusculas() {

		String padrao = " touppercase { initials {  \"Maria da silva Santos\"  } } ";
		Value value = chamarVisitor(padrao, getCliente());
		assertEquals("MDSS", value.asString());

	}
	
	@Test
	public void deveAbreviarTextoGrande() {

		String padrao = " abbreviate {  \"Maria da silva Santos\", 10, 20 } ";
		Value value = chamarVisitor(padrao, getCliente());
		assertEquals("Maria da silva", value.asString());

	}

	@Test
	public void deveGerarErro_FuncaoNaoExiste() {

		String padrao = " capitalize { jsonX { \"$.cliente.nome\" }  } ";
		
		Exception exception = assertThrows(ParseCancellationException.class, () -> {
			Value value = chamarVisitor(padrao, getCliente());
		});
		
		assertTrue(exception.getMessage().contains("token recognition error"));
		
	}

	@Test
	public void deveGerarErro_PalavraNaoReservadaForaDeExpressao() {
		
		String padrao = " capitalize { json XPTO { \"$.cliente.nome\" }  } ";
		
		Exception exception = assertThrows(ParseCancellationException.class, () -> {
			Value value = chamarVisitor(padrao, getCliente());
		});
		
		assertTrue(exception.getMessage().contains("token recognition error"));

	}
	
	@Test
	public void deveGerarErro_IfNaoTemBoolean() {
		
		String padrao = " \"Maria da Silva\" ? true : false ";
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			Value value = chamarVisitor(padrao, getCliente());
		});
		
		assertEquals("expressao deve retornar um boolean :: " + "Maria da Silva", exception.getMessage());

	}
	
	@Test
	public void deveFormatarParaRealBrasileiro() {
		
		String padrao = " formatcurrency { 123.12 , \"pt\" , \"br\" } ";
		Value value = chamarVisitor(padrao, getCliente());
		assertTrue( "R$ 123,12".equals(value.asString()));
		
	}
	
	@Test
	public void deveGerarErroAoTentarFormatarTextoParaMoeda() {
		
		String padrao = " formatcurrency { \"Maria\" , \"pt\" , \"br\" } ";

		Exception exception = assertThrows(RuntimeException.class, () -> {
			Value value = chamarVisitor(padrao, getCliente());
		});
		
		assertEquals("valor deve ser numero/decimal :: " + "Maria", exception.getMessage());

	}
	
	

}
