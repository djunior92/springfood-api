package com.course.springfood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FotoProdutoModel {

    @ApiModelProperty(example = "f8d0c54d-ffed-40e1-8bba-e9e8771e3ee7_Curry.jpg")
    private String nomeArquivo;

    @ApiModelProperty(example = "Curry")
    private String descricao;

    @ApiModelProperty(example = "image/jpeg")
    private String contentType;

    @ApiModelProperty(example = "203015")
    private Long tamanho;

}