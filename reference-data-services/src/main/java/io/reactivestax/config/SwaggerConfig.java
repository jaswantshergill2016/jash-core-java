package io.reactivestax.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix = "api")
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
                .paths(PathSelectors.ant("/api/v1/**")).build().apiInfo(apiInfo());

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

    public String getContact() {
        return contact;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }



    public String getLicense() {
        return license;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }

    public void setContact(final String contact) {
        this.contact = contact;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setLicense(final String license) {
        this.license = license;
    }

    public void setLicenseUrl(final String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public void setTermsOfServiceUrl(final String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public void setVersion(final String version) {
        this.version = version;
    }
}
