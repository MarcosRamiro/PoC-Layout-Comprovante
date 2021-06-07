package com.ramiro.poclayoutcomprovante.model;

public class ResultadoVisitor {
	
	private boolean status;
	private String resultado;
	private String mensagemErro;
	
	public static ResultadoVisitor comStatus(boolean status) {
	 ResultadoVisitor resultado =new ResultadoVisitor();
	 resultado.setStatus(status);
	 return resultado;
	}
	
	public ResultadoVisitor comMensagemDeErro(String mensagemDeErro) {
		this.mensagemErro = mensagemDeErro;
		return this;
	}
	
	public ResultadoVisitor comResultado(String resultado) {
		this.resultado = resultado;
		return this;
	}
	
	public boolean Sucesso() {
		return status;
	}
	
	private void setStatus(boolean status) {
		this.status = status;
	}
	public String getResultado() {
		return resultado;
	}

	public String getMensagemErro() {
		return mensagemErro;
	}
	
	
}
