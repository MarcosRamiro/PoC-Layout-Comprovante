package com.ramiro.PoCLayoutComprovante;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.EnumSet;
import java.util.Set;

@SpringBootApplication
public class PoCLayoutComprovanteApplication {

	public static void main(String[] args) {

		SpringApplication.run(PoCLayoutComprovanteApplication.class, args);

	}

}
