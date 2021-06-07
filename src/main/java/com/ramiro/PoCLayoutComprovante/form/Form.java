package com.ramiro.poclayoutcomprovante.form;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Form {
	
	private Object comprovante;
	private TemplateForm template;
	
	@JsonGetter("comprovante")
	public Object getComprovante() {
		return comprovante;
	}
	@JsonSetter("comprovante")
	public void setComprovante(Object comprovante) {
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
