package com.ramiro.PoCLayoutComprovante.dto;

public class DetalheGrupoTipoBlocoDto implements DetalheGrupoDto {

	private int ordenacao;
	private boolean visibilidade;
	private String tituloAtributo;
	private String valorAtributo;

	public DetalheGrupoTipoBlocoDto() {
		super();
	}

	public String getTituloAtributo() {
		return tituloAtributo;
	}
	public void setTituloAtributo(String tituloAtributo) {
		this.tituloAtributo = tituloAtributo;
	}
	public String getValorAtributo() {
		return valorAtributo;
	}
	public void setValorAtributo(String valorAtributo) {
		this.valorAtributo = valorAtributo;
	}
	public int getOrdenacao() {
		return ordenacao;
	}

	public void setOrdenacao(int ordenacao) {
		this.ordenacao = ordenacao;
	}

	public boolean isVisibilidade() {
		return visibilidade;
	}

	public void setVisibilidade(boolean visibilidade) {
		this.visibilidade = visibilidade;
	}
}
