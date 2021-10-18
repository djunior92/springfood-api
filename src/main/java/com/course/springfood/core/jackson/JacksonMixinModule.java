package com.course.springfood.core.jackson;

import com.course.springfood.api.model.mixin.CidadeMixin;
import com.course.springfood.api.model.mixin.CozinhaMixin;
import com.course.springfood.domain.model.Cidade;
import com.course.springfood.domain.model.Cozinha;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public JacksonMixinModule() {
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
    }

}