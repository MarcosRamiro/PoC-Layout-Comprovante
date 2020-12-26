package com.ramiro.PoCLayoutComprovante.model;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "detalhe_grupo")
public class DetalheGrupo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "detalhe_grupo_id")
	private Long detalheGrupoId;

	@ManyToOne
	@JoinColumn(name = "grupo_id", referencedColumnName = "grupo_id")
	private Grupo grupo;

	@OneToMany(mappedBy = "detalheGrupo")
	private List<DetalheGrupoConteudo> detalheGrupoConteudo;

	private boolean visibilidade;
	private int ordenacao;

	public List<DetalheGrupoConteudo> getDetalheGrupoConteudo() {
		return detalheGrupoConteudo;
	}

	public void setDetalheGrupoConteudo(List<DetalheGrupoConteudo> detalheGrupoConteudo) {
		this.detalheGrupoConteudo = detalheGrupoConteudo;
	}

	public Long getDetalheGrupoId() {
		return detalheGrupoId;
	}

	public void setDetalheGrupoId(Long detalheGrupoId) {
		this.detalheGrupoId = detalheGrupoId;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public boolean isVisibilidade() {
		return visibilidade;
	}

	public void setVisibilidade(boolean visibilidade) {
		this.visibilidade = visibilidade;
	}

	public int getOrdenacao() {
		return ordenacao;
	}

	public void setOrdenacao(int ordenacao) {
		this.ordenacao = ordenacao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String obterConteudoDoAtributo(String atributo) {

		Optional<DetalheGrupoConteudo> findFirst = detalheGrupoConteudo.stream()
				.filter(c -> c.getDominioAtributoDetalheGrupo().getTituloAtributo().equals(atributo)).findFirst();
		return findFirst.isPresent() ? findFirst.get().getConteudo() : "";

	}

}
