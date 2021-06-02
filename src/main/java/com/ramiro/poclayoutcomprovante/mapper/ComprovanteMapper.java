package com.ramiro.poclayoutcomprovante.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ramiro.poclayoutcomprovante.dto.ComprovanteDto;
import com.ramiro.poclayoutcomprovante.dto.GrupoDto;
import com.ramiro.poclayoutcomprovante.form.ComprovanteT3;
import com.ramiro.poclayoutcomprovante.model.Comprovante;

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
