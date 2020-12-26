package com.ramiro.PoCLayoutComprovante.dto;

public class DetalheGrupoTipoTextoDto implements DetalheGrupoDto {

    private int ordenacao;
    private boolean visibilidade;
    private String texto;

    public DetalheGrupoTipoTextoDto(){
        super();
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
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
