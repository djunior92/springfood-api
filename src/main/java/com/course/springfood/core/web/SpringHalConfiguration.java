package com.course.springfood.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;
import org.springframework.http.MediaType;

@Configuration
public class SpringHalConfiguration {

    @Bean
    public HalConfiguration globalPolicy() {
        return new HalConfiguration()
                .withMediaType(MediaType.APPLICATION_JSON)
                .withMediaType(SpringMediaTypes.V1_APPLICATION_JSON)
                .withMediaType(SpringMediaTypes.V2_APPLICATION_JSON);
    }

}