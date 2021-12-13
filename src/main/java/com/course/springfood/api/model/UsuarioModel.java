package com.course.springfood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "José da Silva")
    private String nome;

    @ApiModelProperty(example = "jose.silva@springfood.com")
    private String email;

}