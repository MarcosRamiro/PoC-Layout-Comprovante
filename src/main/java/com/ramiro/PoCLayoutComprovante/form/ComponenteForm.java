package com.ramiro.poclayoutcomprovante.form;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class ComponenteForm {
	
	private String tipo;
	private String nome;
	private String titulo;
	private Integer ordenacao;
	private String visibilidade;
	List<ComponenteAtributoForm> dados;

	@JsonGetter("titulo")
	public String getTitulo() {
		return titulo;
	}
	@JsonSetter("titulo")
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	@JsonGetter("tipo")
	public String getTipo() {
		return tipo;
	}
	@JsonSetter("tipo")
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	@JsonGetter("nome")
	public String getNome() {
		return nome;
	}
	@JsonSetter("nome")
	public void setNome(String nome) {
		this.nome = nome;
	}
	@JsonGetter("ordenacao")
	public Integer getOrdenacao() {
		return ordenacao;
	}
	@JsonSetter("ordenacao")
	public void setOrdenacao(Integer ordenacao) {
		System.out.println("setOrdenacao " + ordenacao);
		this.ordenacao = ordenacao;
	}
	@JsonGetter("visibilidade")
	public String getVisibilidade() {
		return visibilidade;
	}
	@JsonSetter("visibilidade")
	public void setVisibilidade(String visibilidade) {
		this.visibilidade = visibilidade;
	}
	@JsonGetter("dados")
	public List<ComponenteAtributoForm> getDados() {
		return dados;
	}
	@JsonSetter("dados")
	public void setDados(List<ComponenteAtributoForm> dados) {
		this.dados = dados;
	}
	
}
