package com.course.springfood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProdutoModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Camarão")
    private String nome;

    @ApiModelProperty(example = "Porção de camarões")
    private String descricao;

    @ApiModelProperty(example = "110.00")
    private BigDecimal preco;

    @ApiModelProperty(example = "true")
    private Boolean ativo;

}