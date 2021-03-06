# Hanchor RESTful Web Service

[![Java Build](https://img.shields.io/badge/Java-Spring%20Boot-orange)](https://spring.io/projects/spring-boot) [![Spring JPA](https://img.shields.io/badge/Spring-JPA-blue)](https://spring.io/projects/spring-data-jpa) [![Model Mapper ](https://img.shields.io/badge/ModelMapper-%20passing-green)](http://modelmapper.org/) [![Postgres](https://img.shields.io/badge/Postgres-%20SQL-blue)](https://www.postgresql.org/) [![Swagger](https://img.shields.io/badge/Swagger-passing-green)](https://swagger.io/)  [![Amazon Simple Email Service](https://img.shields.io/badge/Amazon-SES-orange)](https://aws.amazon.com/ses/)

This is a simple RESTful Web Service with Spring Boot - That inspires life.

## Features
* HTTP (POST, GET, PUT, DELETE) methods application in Rest Controller.
* Spring Security Implementation securing endpoints.
* JWT token application.
* Password encryption using BCryptPasswordEncoder.
* Authentication and Authorization during login.
* Database relations (One to One, Many to Many...)
* Roles and authorities implementation.
* Swagger Configuration.
* Amazon SES application during email verification and password reset request.
* Flyway for database migration.
* Cloudinary for storing images.

## Prerequisite
To build this project, you require:
- Endeavor to use your own ***accessKeyId*** && ***secretKey*** in AmazonSES class or uncomment AmazonSES usage.
- I have also disabled my Cloudinary ***cloudName***, ***accessKeyId*** && ***secretKey*** in HanchorApplication.
- Knowledge in creating postgres database or using an in memory database H2-database.
- IntelliJ IDEA 2021.1.3 or above
- Maven 4.0.0 or above

## Test it out
Test this app on https://hanchor.herokuapp.com

## Libraries
*   [Spring Boot Web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web)
*   [Model Mapper](http://modelmapper.org/)
*   [Jackson dataformat](https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-xml)
*   [Spring JPA](https://spring.io/projects/spring-data-jpa)
*   [Postgres](https://www.postgresql.org/)
*   [Swagger](https://swagger.io/)
*   [Amazon SES](https://aws.amazon.com/ses/)
*   [Cloudinary](https://aws.amazon.com/ses/https://cloudinary.com/documentation/image_upload_api_reference)


## Other projects
https://github.com/ikechiU?tab=repositories

## Author
Ikechi Ucheagwu 