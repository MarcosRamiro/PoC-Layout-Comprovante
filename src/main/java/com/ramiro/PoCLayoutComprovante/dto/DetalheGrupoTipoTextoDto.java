package com.ramiro.PoCLayoutComprovante.dto;

import com.ramiro.PoCLayoutComprovante.service.ServiceBind;

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

    @Override
    public void tratarAtributos(ServiceBind serviceBind, String json) {
        this.setTexto(serviceBind.bind(this.getTexto(), json));
    }
}
