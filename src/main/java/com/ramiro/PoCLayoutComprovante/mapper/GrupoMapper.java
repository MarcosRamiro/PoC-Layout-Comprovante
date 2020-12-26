package com.ramiro.PoCLayoutComprovante.mapper;

import com.ramiro.PoCLayoutComprovante.dto.GrupoDto;
import com.ramiro.PoCLayoutComprovante.model.Grupo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GrupoMapper {

    @Autowired
    private DetalheGrupoMapper detalheGrupoMapper;

    public List<GrupoDto> transformar(List<Grupo> grupos) {

        List<GrupoDto> listGruposDto = new ArrayList<GrupoDto>();

        for (Grupo grupo : grupos) {
            GrupoDto grupoDto = preencher(grupo);
            listGruposDto.add(grupoDto);
        }

        return listGruposDto;
    }

    private GrupoDto preencher(Grupo grupo) {

        GrupoDto grupoDto = new GrupoDto();
        grupoDto.setTitulo(grupo.getTitulo());
        grupoDto.setTipo(grupo.getTipo());
        grupoDto.setOrdenacao(grupo.getOrdenacao());

        //if(! (grupo.getTipo().equals("linha_horizontal") || grupo.getTipo().equals("boleto"))){
        grupoDto.setDetalhesGrupos(detalheGrupoMapper.transformar(grupo));
        //}
        return grupoDto;

    }
}
