package com.ramiro.poclayoutcomprovante.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ramiro.poclayoutcomprovante.form.TemplateForm;
import com.ramiro.poclayoutcomprovante.dto.TemplateDto;

@Service
public class ComprovanteBinder {

	@Autowired
	private ServiceBind serviceBind;

	public TemplateDto bind(Object comprovante, TemplateForm templateForm) throws ServiceBindException {

		templateForm.getComponentes().stream()
		
				.forEach(componente -> {
					
					if (componente.getTitulo() != null && !componente.getTitulo().isEmpty())
						componente.setTitulo(this.encapsulaTry(serviceBind, comprovante,componente.getTitulo()));
					
					if (!componente.getTipo().equals("header") && !componente.getTipo().equals("footer")) {

						if (componente.getVisibilidade() != null && !componente.getVisibilidade().isEmpty())
							componente.setVisibilidade( this.encapsulaTry(serviceBind, comprovante,  componente.getVisibilidade() ));

						if (componente.getDados() != null) {
							componente.getDados().stream().forEach(compAtributo -> {

								if (compAtributo.getRotulo() != null && !compAtributo.getRotulo().isEmpty())
									compAtributo.setRotulo(this.encapsulaTry(serviceBind, comprovante,  compAtributo.getRotulo()));

								if (compAtributo.getVisibilidade() != null && !compAtributo.getVisibilidade().isEmpty())
									compAtributo.setVisibilidade(this.encapsulaTry(serviceBind, comprovante,compAtributo.getVisibilidade()));

								if (compAtributo.getConteudo() != null && !compAtributo.getConteudo().isEmpty())
									compAtributo.setConteudo(this.encapsulaTry(serviceBind, comprovante,compAtributo.getConteudo()));
							});
						}
					}
						

				});

		TemplateDto templateDto = TemplateDto.of(templateForm);

		return templateDto;
	}
	
	private String encapsulaTry(ServiceBind serviceBind, Object comprovante, String padrao)  {
		try {
			return serviceBind.bind(padrao, comprovante);
		}catch (ServiceBindException e ) {
			return padrao;
		}
	
	}

}
