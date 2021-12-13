package com.course.springfood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeResumoModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Catanduva")
    private String nome;

    @ApiModelProperty(example = "São Paulo")
    private String estado;

}