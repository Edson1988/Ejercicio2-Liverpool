package com.Liverpool2.DOCUMENTACION;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
	
	 @Bean
	 public GroupedOpenApi apiClientes() {
	  return GroupedOpenApi.builder()
	    .group("clientes")
	    .pathsToMatch("/api/clientes/**")
	    .build();
	 }

	 @Bean
	 public GroupedOpenApi apiPedidos() {
	  return GroupedOpenApi.builder()
	    .group("pedidos")
	    .pathsToMatch("/api/pedidos/**")
	    .build();
	 }

}
