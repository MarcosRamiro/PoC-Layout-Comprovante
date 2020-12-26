package com.ramiro.PoCLayoutComprovante.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ramiro.PoCLayoutComprovante.dto.ComprovanteDto;
import com.ramiro.PoCLayoutComprovante.form.ComprovanteT3;
import com.ramiro.PoCLayoutComprovante.model.Comprovante;
import com.ramiro.PoCLayoutComprovante.repository.ComprovanteRepository;
import com.ramiro.PoCLayoutComprovante.service.ComprovanteBinder;

@RestController
@RequestMapping("/comprovante/{id}")
public class ComprovanteController {
	
	@Autowired
	private ComprovanteBinder comprovanteBinder;
	@Autowired
	private ComprovanteRepository comprovanteRepository;
	
	@GetMapping
	public ResponseEntity<ComprovanteDto> obterComprovante(@RequestBody ComprovanteT3 comprovanteT3) {
		
		Optional<Comprovante> comprovante = comprovanteRepository.findById(1L);
		if(comprovante.isPresent())		
			return ResponseEntity.ok(comprovanteBinder.bind(comprovanteT3, comprovante.get()));
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
	}

}
