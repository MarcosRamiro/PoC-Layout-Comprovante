package com.ramiro.poclayoutcomprovante.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.ramiro.poclayoutcomprovante.form.ComponenteAtributoForm;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComponenteAtributoDto {

	private String rotulo;
	private String conteudo;
	private Boolean visibilidade;
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
	public Boolean isVisibilidade() {
		return visibilidade;
	}
	@JsonSetter("visibilidade")
	public void setVisibilidade(Boolean visibilidade) {
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

	public static ComponenteAtributoDto of(ComponenteAtributoForm atributoForm) {
		
		ComponenteAtributoDto componenteAtributoDto = new ComponenteAtributoDto();
		componenteAtributoDto.setConteudo(atributoForm.getConteudo());
		componenteAtributoDto.setOrdenacao(atributoForm.getOrdenacao());
		componenteAtributoDto.setRotulo(atributoForm.getRotulo());
		componenteAtributoDto.setVisibilidade(atributoForm.getVisibilidade() != null ? atributoForm.getVisibilidade().equals("true") : false);
		return componenteAtributoDto;
	}
	
}