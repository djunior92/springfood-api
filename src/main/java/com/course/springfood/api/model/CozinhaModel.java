package com.course.springfood.api.model;

import com.course.springfood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaModel {

    @ApiModelProperty(example = "1")
    @JsonView(RestauranteView.Resumo.class)
    private Long id;

    @ApiModelProperty(example = "Tailandesa")
    @JsonView(RestauranteView.Resumo.class)
    private String nome;

}