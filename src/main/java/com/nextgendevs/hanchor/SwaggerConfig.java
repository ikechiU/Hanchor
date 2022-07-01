package com.nextgendevs.hanchor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    Contact contact = new Contact(
            "Group 1",
            "https://github.com/orgs/NGDevs/repositories",
            "https://github.com/orgs/NGDevs"
    );

    List<VendorExtension> vendorExtensions = new ArrayList<>();

    ApiInfo apiInfo = new ApiInfo(
            "Hanchor RESTful Web Service documentation.",
            "This pages documents Hanchor app RESTful Web Service endpoints.\n Use the credential below to test the API endpoints.\n\n" +
                    "email: <strong>admin@hanchor.com</strong> \n" +
                    "userId: <strong>123456789</strong> \n" +
                    "password: <strong>123456</strong>",
            "1.0",
            "http://www.appsdeveloperblog.com/service.html",
            contact,
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            vendorExtensions);


    @Bean
    public Docket apiDocket() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo) //Extra documentation features
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nextgendevs.hanchor"))
                .paths(PathSelectors.any())
                .build();

        return docket;

    }

}
