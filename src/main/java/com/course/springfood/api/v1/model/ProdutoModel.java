package com.course.springfood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "produtos")
@Setter
@Getter
public class ProdutoModel extends RepresentationModel<ProdutoModel> {

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