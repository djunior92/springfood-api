package com.course.springfood;

import com.course.springfood.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class SpringfoodApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringfoodApiApplication.class, args);
    }

}