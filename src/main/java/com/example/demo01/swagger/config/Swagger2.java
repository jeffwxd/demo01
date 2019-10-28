package com.example.demo01.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
    public class Swagger2 {
     
    	@Bean
    	public Docket createRestApi() {
    		return new Docket(DocumentationType.SWAGGER_2)
    				.apiInfo(apiInfo())
    				.select()
    				.apis(RequestHandlerSelectors.basePackage("com.example.demo01.controller"))
    				.paths(PathSelectors.any())
    				.build()
					.securitySchemes(securityScheme())
					.securityContexts(securityContexts());
    	}
    	
    	private ApiInfo apiInfo() {
    		return new ApiInfoBuilder()
    				.title("订单接口")
    				.description("订单详细信息")
    				.termsOfServiceUrl("http://www.hahha.com")
    				.version("1.0")
    				.build();
    	}

	private List<ApiKey> securityScheme() {
		return newArrayList(
				new ApiKey("Authorization", "Authorization", "header"));
	}

	private List<SecurityContext> securityContexts() {
		return newArrayList(
				SecurityContext.builder()
						.securityReferences(defaultAuth())
						.forPaths(regex("/*.*"))
						.build()
		);
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return newArrayList(
				new SecurityReference("Authorization", authorizationScopes));
	}
    }