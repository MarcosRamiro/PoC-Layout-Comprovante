package com.ramiro.PoCLayoutComprovante.mapper;

import com.ramiro.PoCLayoutComprovante.dto.ComprovanteDto;
import com.ramiro.PoCLayoutComprovante.dto.GrupoDto;
import com.ramiro.PoCLayoutComprovante.form.ComprovanteT3;
import com.ramiro.PoCLayoutComprovante.model.Comprovante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComprovanteMapper {

    @Autowired
    private GrupoMapper grupoMapper;

    public ComprovanteDto transformar(ComprovanteT3 comprovanteT3, Comprovante comprovante) {

        ComprovanteDto comprovanteDto = new ComprovanteDto();
        comprovanteDto.setId(comprovanteT3.getId());
        comprovanteDto.setTipo(comprovante.getTipo());
        comprovanteDto.setTitulo(comprovante.getTitulo());
        comprovanteDto.setDescricao(comprovante.getDescricao());
        comprovanteDto.setVersao(comprovante.getVersao());
        comprovanteDto.setGrupos(grupoMapper.transformar(comprovante.getGrupos()));

        return comprovanteDto;
    }


}
