package com.ramiro.PoCLayoutComprovante.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.stereotype.Service;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Configuration;

import java.util.List;
import java.util.Map;

@Service
public class ServiceBind {

		public String bind(String padrao, String json) {

			if(padrao.contains("$")) {
				Object obj = JsonPath.read(json, padrao);

					if(obj instanceof List<?>){
						for (Object primeiroObjeto: (List<?>) obj) {
							return primeiroObjeto == null ? "" : primeiroObjeto.toString();
						}
					}

					return obj == null ? "" : obj.toString();
			}
			return padrao;
		}
}
