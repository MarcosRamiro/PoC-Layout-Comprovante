package com.ramiro.poclayoutcomprovante.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "detalhe_grupo_conteudo")
public class DetalheGrupoConteudo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="detalhe_grupo_conteudo_id")
	private Long detalheGrupoConteudoId;
	
	@ManyToOne
	@JoinColumn(name = "detalhe_grupo_id", referencedColumnName = "detalhe_grupo_id")
	private DetalheGrupo detalheGrupo;
	
	@OneToOne
	@JoinColumn(name = "dominio_atributo_detalhe_grupo_id", referencedColumnName = "dominio_atributo_detalhe_grupo_id")
	private DominioAtributoDetalheGrupo dominioAtributoDetalheGrupo;
	
	private String conteudo;

	public Long getDetalheGrupoConteudoId() {
		return detalheGrupoConteudoId;
	}

	public void setDetalheGrupoConteudoId(Long detalheGrupoConteudoId) {
		this.detalheGrupoConteudoId = detalheGrupoConteudoId;
	}

	public DetalheGrupo getDetalheGrupo() {
		return detalheGrupo;
	}

	public void setDetalheGrupo(DetalheGrupo detalheGrupo) {
		this.detalheGrupo = detalheGrupo;
	}

	public DominioAtributoDetalheGrupo getDominioAtributoDetalheGrupo() {
		return dominioAtributoDetalheGrupo;
	}

	public void setDominioAtributoDetalheGrupo(DominioAtributoDetalheGrupo dominioAtributoDetalheGrupo) {
		this.dominioAtributoDetalheGrupo = dominioAtributoDetalheGrupo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

}
