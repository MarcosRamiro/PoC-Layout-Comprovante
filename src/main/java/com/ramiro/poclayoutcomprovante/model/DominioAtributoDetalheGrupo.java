package com.ramiro.poclayoutcomprovante.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dominio_atributo_detalhe_grupo")
public class DominioAtributoDetalheGrupo implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="dominio_atributo_detalhe_grupo_id")
	private Long id;
	
	@Column(name="nome_atributo")
	private String tituloAtributo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTituloAtributo() {
		return tituloAtributo;
	}

	public void setTituloAtributo(String tituloAtributo) {
		this.tituloAtributo = tituloAtributo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
