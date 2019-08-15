package io.reactivestax.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix = "api")
@Conditional(SwaggerConfigEnableCondition.class)
@Getter
@Setter
public class SwaggerConfig {

	private String contact;

	private String description;

	private String email;
	
	private String license;

	private String licenseUrl;

	private String termsOfServiceUrl;
	
	private String title;

	private String url;

	private String version;
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
		        .paths(regex("/api/*.*")).build().apiInfo(apiInfo()).globalOperationParameters(getHeaderParam());

	}

	private ApiInfo apiInfo() {
		return new ApiInfo(getTitle(), //
		        getDescription(), //
		        getVersion(), //
		        getTermsOfServiceUrl(), //
		        new Contact(getContact(), getUrl(), getEmail()), //
		        getLicense(), //
		        getLicenseUrl()

		);
	}

	private List<Parameter> getHeaderParam() {

		List<Parameter> list = new ArrayList();
		Parameter p = new ParameterBuilder()
				.name("request-metadata")
				.description("request header containing request metadata information")
				.modelRef(new ModelRef("string"))
				.parameterType("header")
				.required(true)
				.build();

		list.add(p);

		return list;

	}


}
