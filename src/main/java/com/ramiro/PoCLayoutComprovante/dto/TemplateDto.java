package com.ramiro.poclayoutcomprovante.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.ramiro.poclayoutcomprovante.form.TemplateForm;

public class TemplateDto {
	
	private List<ComponenteDto> componentes;
	
	@JsonGetter("componentes")
	public List<ComponenteDto> getComponentes() {
		return componentes;
	}
	
	@JsonSetter("componentes")
	public void setComponentes(List<ComponenteDto> componentes) {
		this.componentes = componentes;
	}

	public static TemplateDto of(TemplateForm templateForm) {
		
		TemplateDto templateDto = new TemplateDto();
		List<ComponenteDto> lista = new ArrayList<ComponenteDto>();
		
		templateForm.getComponentes().stream()
			.forEach(componenteForm -> lista.add(ComponenteDto.of(componenteForm)));
		
		templateDto.setComponentes(lista);
		
		return templateDto;
	}
	
}
