package br.com.person.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket swaggerPersonApiv1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("rest-api-person-1.0")
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.person.api.controller"))
                .paths(PathSelectors.ant("/api/v1/**"))
                .build()
                .apiInfo(this.informacoesApiV1().build())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessageForGET());
    }

    @Bean
    public Docket swaggerPersonApiv2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("rest-api-person-2.0")
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.person.api.controller"))
                .paths(PathSelectors.ant("/api/v2/**"))
                .build()
                .apiInfo(this.informacoesApiV2().build())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessageForGET());
    }

    private ApiInfoBuilder informacoesApiV1() {

        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();

        apiInfoBuilder.title("API REST - Person");
        apiInfoBuilder.description("API REST - Person");
        apiInfoBuilder.version("1.0");
        apiInfoBuilder.license("Licença - PRIVADO");
        apiInfoBuilder.contact(this.contato());

        return apiInfoBuilder;
    }

    private ApiInfoBuilder informacoesApiV2() {

        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();

        apiInfoBuilder.title("API REST - Person");
        apiInfoBuilder.description("API REST - Person");
        apiInfoBuilder.version("2.0");
        apiInfoBuilder.license("Licença - PRIVADO");
        apiInfoBuilder.contact(this.contato());

        return apiInfoBuilder;
    }

    private Contact contato() {

        return new Contact(
                "Mohammad Shadnik",
                "https://github.com/MohShad",
                "mohammad.shadnik@gmail.com");
    }

    private List<ResponseMessage> responseMessageForGET() {
        List<ResponseMessage> responseMessages = new ArrayList<>();

        responseMessages.add(new ResponseMessageBuilder()
                .code(500)
                .message("Erro inesperado")
                .responseModel(new ModelRef("string"))
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(403)
                .message("Você não tem permissão para acessar este link")
                .build());

        return responseMessages;
    }
}
