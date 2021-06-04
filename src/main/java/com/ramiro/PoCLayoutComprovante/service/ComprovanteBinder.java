package com.ramiro.poclayoutcomprovante.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ramiro.poclayoutcomprovante.form.ComprovanteT3;
import com.ramiro.poclayoutcomprovante.form.TemplateForm;
import com.ramiro.poclayoutcomprovante.dto.TemplateDto;

@Service
public class ComprovanteBinder {

	@Autowired
	private ServiceBind serviceBind;

	public TemplateDto bind(ComprovanteT3 comprovanteT3, TemplateForm templateForm) {

		templateForm.getComponentes().stream()
		
				.forEach(componente -> {
					
					if (componente.getTitulo() != null && !componente.getTitulo().isEmpty())
						componente.setTitulo(serviceBind.bind(componente.getTitulo(), comprovanteT3));
					
					if (!componente.getTipo().equals("header") && !componente.getTipo().equals("footer")) {

						if (componente.getVisibilidade() != null && !componente.getVisibilidade().isEmpty())
							componente.setVisibilidade(serviceBind.bind(componente.getVisibilidade(), comprovanteT3));

						if (componente.getDados() != null) {
							componente.getDados().stream().forEach(compAtributo -> {

								if (compAtributo.getRotulo() != null && !compAtributo.getRotulo().isEmpty())
									compAtributo.setRotulo(serviceBind.bind(compAtributo.getRotulo(), comprovanteT3));

								if (compAtributo.getVisibilidade() != null && !compAtributo.getVisibilidade().isEmpty())
									compAtributo.setVisibilidade(serviceBind.bind(compAtributo.getVisibilidade(), comprovanteT3));

								if (compAtributo.getConteudo() != null && !compAtributo.getConteudo().isEmpty())
									compAtributo.setConteudo(serviceBind.bind(compAtributo.getConteudo(), comprovanteT3));
							});
						}
					}
						

				});

		TemplateDto templateDto = TemplateDto.of(templateForm);

		return templateDto;
	}

}
