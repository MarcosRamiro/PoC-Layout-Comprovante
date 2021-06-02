package com.ramiro.poclayoutcomprovante.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;

public class GrupoDto {
	
	private int ordenacao;
	private String titulo;
	private String tipo;
	private List<DetalheGrupoDto> detalhesGrupos;
		
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getOrdenacao() {
		return ordenacao;
	}

	public void setOrdenacao(int ordenacao) {
		this.ordenacao = ordenacao;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@JsonGetter("detalhes")
	public List<DetalheGrupoDto> getDetalhesGrupos() {
		return detalhesGrupos;
	}

	public void setDetalhesGrupos(List<DetalheGrupoDto> detalhesGrupos) {
		this.detalhesGrupos = detalhesGrupos;
	}

}
