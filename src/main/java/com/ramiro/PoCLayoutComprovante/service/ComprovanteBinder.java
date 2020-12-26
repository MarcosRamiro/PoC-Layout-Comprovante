package com.ramiro.PoCLayoutComprovante.service;

import java.util.List;

import com.ramiro.PoCLayoutComprovante.dto.*;
import com.ramiro.PoCLayoutComprovante.mapper.ComprovanteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.GsonBuilder;
import com.ramiro.PoCLayoutComprovante.form.ComprovanteT3;
import com.ramiro.PoCLayoutComprovante.model.Comprovante;

@Service
public class ComprovanteBinder {

	@Autowired
	private ServiceBind serviceBind;
	@Autowired
	private ComprovanteMapper comprovanteMapper;

	private String json;

	public ComprovanteDto bind(ComprovanteT3 comprovanteT3, Comprovante comprovante) {

		this.json= new GsonBuilder().setPrettyPrinting().create().toJson(comprovanteT3);

		ComprovanteDto comprovanteDto = comprovanteMapper.transformar(comprovante);

		comprovanteDto.setId(comprovanteT3.getId());

		comprovanteDto.setTitulo(serviceBind.bind(comprovanteDto.getTitulo(), json));
		comprovanteDto.setId(serviceBind.bind(comprovanteDto.getId(), json));
		comprovanteDto.setTipo(serviceBind.bind(comprovanteDto.getTipo(), json));
		comprovanteDto.setVersao(serviceBind.bind(comprovanteDto.getVersao(), json));

		comprovanteDto.getGrupos()
				.stream()
				.forEach(this::tratarGrupos);

		return comprovanteDto;
	}

	private void tratarGrupos(GrupoDto grupo) {

		grupo.setTitulo(serviceBind.bind(grupo.getTitulo(), json));
		tratarDetalhesGrupos(grupo.getDetalhesGrupos());

	}

	private void tratarDetalhesGrupos(List<DetalheGrupoDto> detalhes) {

		if(detalhes == null)
			return;

		for (DetalheGrupoDto detalhe : detalhes) {
			detalhe.tratarAtributos(serviceBind, json);
		}

	}
}
