package com.exuberant.rest.survey.kickoff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.exuberant.rest"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}