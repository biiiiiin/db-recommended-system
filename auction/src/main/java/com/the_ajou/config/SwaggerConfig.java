package com.the_ajou.config;

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
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
//                .securityContexts(Arrays.asList(securityContext())) // 추가
//                .securitySchemes(Arrays.asList(apiKey())) // 추가
                .useDefaultResponseMessages(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.the_ajou.web.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

//    //추가
//    private SecurityContext securityContext() {
//        return SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                .build();
//    }
//
//    // 추가
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
//    }
//
//    // 추가
//    private ApiKey apiKey() {
//        return new ApiKey("Authorization", "Authorization", "header");
//    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("The Ajou Swagger")
                .description("the ajou swagger config")
                .version("1.0.2")
                .build();
    }
}
