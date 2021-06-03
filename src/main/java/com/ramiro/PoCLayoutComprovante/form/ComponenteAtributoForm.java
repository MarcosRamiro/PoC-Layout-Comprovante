package com.ramiro.poclayoutcomprovante.form;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class ComponenteAtributoForm {

	private String rotulo;
	private String conteudo;
	private String visibilidade;
	private Integer ordenacao;
	
	@JsonGetter("rotulo")
	public String getRotulo() {
		return rotulo;
	}
	@JsonSetter("rotulo")
	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}
	@JsonGetter("conteudo")
	public String getConteudo() {
		return conteudo;
	}
	@JsonSetter("conteudo")
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	@JsonGetter("visibilidade")
	public String getVisibilidade() {
		return visibilidade;
	}
	@JsonSetter("visibilidade")
	public void setVisibilidade(String visibilidade) {
		this.visibilidade = visibilidade;
	}
	@JsonGetter("ordenacao")
	public Integer getOrdenacao() {
		return ordenacao;
	}
	@JsonSetter("ordenacao")
	public void setOrdenacao(Integer ordenacao) {
		this.ordenacao = ordenacao;
	}
	
}
