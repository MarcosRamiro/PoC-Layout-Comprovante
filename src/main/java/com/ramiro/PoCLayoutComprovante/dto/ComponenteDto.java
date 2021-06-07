package com.ramiro.poclayoutcomprovante.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;
import com.ramiro.poclayoutcomprovante.form.ComponenteForm;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComponenteDto {
	
	private String tipo;
	private String nome;
	private String titulo;
	private Integer ordenacao;
	private Boolean visibilidade;
	List<ComponenteAtributoDto> dados;
	
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
		this.ordenacao = ordenacao;
	}
	
	@JsonGetter("visibilidade")
	public Boolean isVisibilidade() {
		return visibilidade;
	}

	@JsonSetter("visibilidade")
	public void setVisibilidade(Boolean visibilidade) {
		this.visibilidade = visibilidade;
	}
	
	@JsonGetter("dados")
	public List<ComponenteAtributoDto> getDados() {
		return dados;
	}
	
	@JsonSetter("dados")
	public void setDados(List<ComponenteAtributoDto> dados) {
		this.dados = dados;
	}

	public static ComponenteDto of(ComponenteForm componenteForm) {
		
		ComponenteDto componenteDto = new ComponenteDto();
		componenteDto.setNome(componenteForm.getNome());
		componenteDto.setOrdenacao(componenteForm.getOrdenacao());
		componenteDto.setTipo(componenteForm.getTipo());
		componenteDto.setVisibilidade(componenteForm.getVisibilidade() != null ? componenteForm.getVisibilidade().equals("true") : null);
		componenteDto.setTitulo(componenteForm.getTitulo());

		if (componenteForm.getDados() != null) {
			
			List<ComponenteAtributoDto> lista = new ArrayList<ComponenteAtributoDto>();
			
			componenteForm.getDados().stream()
				.forEach(atributoForm -> lista.add(ComponenteAtributoDto.of(atributoForm)));
		
			componenteDto.setDados(lista);
		}
		
		return componenteDto;
	}
	
	
	
}
