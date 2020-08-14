package com.neo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("com.neo.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("组织机构管理")
                .description("keycloak管理中心 API 9.0.2 操作文档")
                //服务条款网址
                .termsOfServiceUrl("https://www.keycloak.org/getting-started")
                .version("9.0.2")
                //.contact(new Contact("KeyCloak架构介绍", "https://blog.csdn.net/woxinqidai/article/details/84137747?depth_1-utm_source=distribute.pc_relevant.none-task&utm_source=distribute.pc_relevant.none-task", ""))
                .contact(new Contact("官网API", "https://www.keycloak.org/docs-api/9.0/rest-api/index.html", ""))
                .build();
    }
}