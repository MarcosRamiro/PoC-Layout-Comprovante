package com.ramiro.PoCLayoutComprovante.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.ramiro.PoCLayoutComprovante.mapper.ComprovanteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ramiro.PoCLayoutComprovante.dto.ComprovanteDto;
import com.ramiro.PoCLayoutComprovante.form.ComprovanteT3;
import com.ramiro.PoCLayoutComprovante.model.Comprovante;
import com.ramiro.PoCLayoutComprovante.repository.ComprovanteRepository;
import com.ramiro.PoCLayoutComprovante.service.ComprovanteBinder;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequestMapping("/comprovante")
public class ComprovanteController {
	
	@Autowired
	private ComprovanteBinder comprovanteBinder;
	@Autowired
	private ComprovanteRepository comprovanteRepository;
	@Autowired
	ComprovanteMapper comprovanteMapper;

	private String tempo;
	
	@GetMapping("/detalhe")
	public ResponseEntity<ComprovanteDto> obterComprovante(@RequestBody ComprovanteT3 comprovanteT3) {

		System.out.println("ComprovanteT3.Id -> " + comprovanteT3.getId());

		List<Comprovante> listaComprovante = comprovanteRepository.findByTipoIgnoreCaseAndVersaoIgnoreCase(comprovanteT3.getTipo(), comprovanteT3.getVersao());
		if(listaComprovante != null && listaComprovante.stream().count() > 0) {
			Optional<Comprovante> primeiroComprovante = listaComprovante.stream().findFirst();

			if(primeiroComprovante.isPresent()){
				return ResponseEntity.ok(comprovanteBinder.bind(comprovanteT3, primeiroComprovante.get()));
			}
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@GetMapping("/layout/{id}")
	public ResponseEntity<ComprovanteDto> obterLayoutComprovante(@PathVariable("id") String id) {

		Optional<Comprovante> comprovante = comprovanteRepository.findById(Long.parseLong(id));

		if(comprovante.isPresent())
			return ResponseEntity.ok(comprovanteMapper.transformar(comprovante.get()));

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}

}
