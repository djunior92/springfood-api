package com.course.springfood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoModel {

    @ApiModelProperty(example = "15800-000")
    private String cep;

    @ApiModelProperty(example = "Rua Brasil")
    private String logradouro;

    @ApiModelProperty(example = "\"100\"")
    private String numero;

    @ApiModelProperty(example = "Apto 1050")
    private String complemento;

    @ApiModelProperty(example = "Centro")
    private String bairro;

    private CidadeResumoModel cidade;

}