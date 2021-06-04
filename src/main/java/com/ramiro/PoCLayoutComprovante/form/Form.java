package com.ramiro.poclayoutcomprovante.form;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Form {
	
	private ComprovanteT3 comprovante;
	private TemplateForm template;
	
	@JsonGetter("comprovante")
	public ComprovanteT3 getComprovante() {
		return comprovante;
	}
	@JsonSetter("comprovante")
	public void setComprovante(ComprovanteT3 comprovante) {
		this.comprovante = comprovante;
	}
	@JsonGetter("template")
	public TemplateForm getTemplate() {
		return template;
	}
	@JsonSetter("template")
	public void setTemplate(TemplateForm template) {
		this.template = template;
	}
	
	

}
