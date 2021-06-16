package com.ramiro.poclayoutcomprovante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ramiro.poclayoutcomprovante.form.Form;
import com.ramiro.poclayoutcomprovante.dto.TemplateDto;
import com.ramiro.poclayoutcomprovante.service.ComprovanteBinder;
import com.ramiro.poclayoutcomprovante.service.ServiceBindException;

@RestController
@RequestMapping("/comprovante")
public class ComprovanteController {
	
	@Autowired
	private ComprovanteBinder comprovanteBinder;

	@CrossOrigin(origins = "*")
	@PostMapping("/detalhe")
	public ResponseEntity<TemplateDto> obterComprovante(@RequestBody Form form) throws ServiceBindException {

		TemplateDto templateDto = comprovanteBinder.bind(form.getComprovante(), form.getTemplate());
		return ResponseEntity.ok(templateDto);

	}

}
