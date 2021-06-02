package com.ramiro.poclayoutcomprovante.model;

import java.io.Serializable;
import java.util.List;

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
@Table(name = "grupo")
public class Grupo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="grupo_id")
	private long grupoId;
	
	@ManyToOne
	@JoinColumn(name="comprovante_id", referencedColumnName="comprovante_id")
	private Comprovante comprovante;
	
	private String titulo;
	private String tipo;
	private int ordenacao;
	private boolean visibilidade;
	
	@OneToMany(mappedBy = "grupo")
	private List<DetalheGrupo> detalhes;

	public boolean isVisibilidade() {
		return visibilidade;
	}
	public void setVisibilidade(boolean visibilidade) {
		this.visibilidade = visibilidade;
	}
	public List<DetalheGrupo> getDetalhes() {
		return detalhes;
	}
	public void setDetalhes(List<DetalheGrupo> detalhes) {
		this.detalhes = detalhes;
	}
	public long getGrupoId() {
		return grupoId;
	}
	public void setGrupoId(long grupoId) {
		this.grupoId = grupoId;
	}
	public Comprovante getComprovante() {
		return comprovante;
	}
	public void setComprovante(Comprovante comprovante) {
		this.comprovante = comprovante;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
