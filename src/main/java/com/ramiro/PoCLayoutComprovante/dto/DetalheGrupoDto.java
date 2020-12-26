package com.ramiro.PoCLayoutComprovante.dto;

import com.ramiro.PoCLayoutComprovante.service.ServiceBind;

public interface DetalheGrupoDto {

    void tratarAtributos(ServiceBind serviceBind, String json);

}
