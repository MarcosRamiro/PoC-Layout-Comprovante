package com.ramiro.poclayoutcomprovante.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import com.ramiro.poclayoutcomprovante.dto.ComprovanteDto;
import com.ramiro.poclayoutcomprovante.form.ComprovanteT3;
import com.ramiro.poclayoutcomprovante.mapper.ComprovanteMapper;
import com.ramiro.poclayoutcomprovante.model.Comprovante;
import com.ramiro.poclayoutcomprovante.repository.ComprovanteRepository;
import com.ramiro.poclayoutcomprovante.service.ComprovanteBinder;

@RestController
@RequestMapping("/comprovante")
public class ComprovanteController {
	
	@Autowired
	private ComprovanteBinder comprovanteBinder;
	@Autowired
	private ComprovanteRepository comprovanteRepository;
	@Autowired
	ComprovanteMapper comprovanteMapper;

	
	@PostMapping("/detalhe")
	public ResponseEntity<ComprovanteDto> obterComprovante(@RequestBody ComprovanteT3 comprovanteT3) {

		Instant inicio = Instant.now();

		List<Comprovante> listaComprovante = comprovanteRepository.findByTipoIgnoreCaseAndVersaoIgnoreCase(comprovanteT3.getTipo(), comprovanteT3.getVersao());
		if(listaComprovante != null && listaComprovante.stream().count() > 0) {
			Optional<Comprovante> primeiroComprovante = listaComprovante.stream().findFirst();

			if(primeiroComprovante.isPresent()){
				ComprovanteDto bind = comprovanteBinder.bind(comprovanteT3, primeiroComprovante.get());
				Instant fim = Instant.now();
				Duration duracao = Duration.between(inicio, fim);
				System.out.println("Tempo -> " + duracao.toMillis());
				return ResponseEntity.ok(bind);
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
