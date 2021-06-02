package com.ramiro.poclayoutcomprovante.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ramiro.poclayoutcomprovante.dto.*;
import com.ramiro.poclayoutcomprovante.form.ComprovanteT3;
import com.ramiro.poclayoutcomprovante.mapper.ComprovanteMapper;
import com.ramiro.poclayoutcomprovante.model.Comprovante;

@Service
public class ComprovanteBinder {

	@Autowired
	private ServiceBind serviceBind;
	@Autowired
	private ComprovanteMapper comprovanteMapper;

	public ComprovanteDto bind(ComprovanteT3 comprovanteT3, Comprovante comprovante) {

		ComprovanteDto comprovanteDto = comprovanteMapper.transformar(comprovante);

		comprovanteDto.setId(comprovanteT3.getId());

		comprovanteDto.setTitulo(serviceBind.bind(comprovanteDto.getTitulo(), comprovanteT3));
		comprovanteDto.setId(serviceBind.bind(comprovanteDto.getId(), comprovanteT3));
		comprovanteDto.setTipo(serviceBind.bind(comprovanteDto.getTipo(), comprovanteT3));
		comprovanteDto.setVersao(serviceBind.bind(comprovanteDto.getVersao(), comprovanteT3));

		comprovanteDto.getGrupos()
				.stream()
				.forEach(detalhe -> tratarGrupos(detalhe, comprovanteT3));

		return comprovanteDto;
	}

	private void tratarGrupos(GrupoDto grupo, ComprovanteT3 comprovanteT3) {

		grupo.setTitulo(serviceBind.bind(grupo.getTitulo(), comprovanteT3));
		tratarDetalhesGrupos(grupo.getDetalhesGrupos(), comprovanteT3);

	}

	private void tratarDetalhesGrupos(List<DetalheGrupoDto> detalhes, ComprovanteT3 comprovanteT3) {

		if(detalhes == null) return;

		for (DetalheGrupoDto detalhe : detalhes) {
			detalhe.tratarAtributos(dado -> serviceBind.bind(dado, comprovanteT3));
		}
	}
}
