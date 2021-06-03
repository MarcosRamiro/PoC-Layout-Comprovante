package com.ramiro.poclayoutcomprovante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ramiro.poclayoutcomprovante.form.ComprovanteT3;
import com.ramiro.poclayoutcomprovante.dto.TemplateDto;
import com.ramiro.poclayoutcomprovante.service.ComprovanteBinder;

@RestController
@RequestMapping("/comprovante")
public class ComprovanteController {
	
	@Autowired
	private ComprovanteBinder comprovanteBinder;

	
	@PostMapping("/detalhe")
	public ResponseEntity<TemplateDto> obterComprovante(@RequestBody ComprovanteT3 comprovanteT3) {

		TemplateDto template = comprovanteBinder.bind(comprovanteT3);
		return ResponseEntity.ok(template);

	}

}
