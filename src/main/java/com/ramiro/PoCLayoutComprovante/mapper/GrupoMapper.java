package com.ramiro.PoCLayoutComprovante.mapper;

import com.ramiro.PoCLayoutComprovante.dto.GrupoDto;
import com.ramiro.PoCLayoutComprovante.model.Grupo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoMapper {

    @Autowired
    private DetalheGrupoMapper detalheGrupoMapper;

    public List<GrupoDto> transformar(List<Grupo> grupos) {

        return grupos.stream()
                .map(this::preencher)
                .collect(Collectors.toList());
    }

    private GrupoDto preencher(Grupo grupo) {

        GrupoDto grupoDto = new GrupoDto();
        grupoDto.setTitulo(grupo.getTitulo());
        grupoDto.setTipo(grupo.getTipo());
        grupoDto.setOrdenacao(grupo.getOrdenacao());
        grupoDto.setDetalhesGrupos(detalheGrupoMapper.transformar(grupo));
        return grupoDto;

    }
}
