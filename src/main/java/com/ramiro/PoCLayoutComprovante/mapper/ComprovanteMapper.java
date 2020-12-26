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

    public ComprovanteDto transformar(Comprovante comprovante) {
        ComprovanteDto comprovanteDto = new ComprovanteDto();
        comprovanteDto.setTipo(comprovante.getTipo());
        comprovanteDto.setTitulo(comprovante.getTitulo());
        comprovanteDto.setDescricao(comprovante.getDescricao());
        comprovanteDto.setVersao(comprovante.getVersao());
        comprovanteDto.setGrupos(grupoMapper.transformar(comprovante.getGrupos()));

        return comprovanteDto;
    }


}
