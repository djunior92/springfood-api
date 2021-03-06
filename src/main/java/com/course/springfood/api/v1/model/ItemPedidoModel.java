package com.course.springfood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel> {

    @ApiModelProperty(example = "1")
    private Long produtoId;

    @ApiModelProperty(example = "Picanha ao alho")
    private String produtoNome;

    @ApiModelProperty(example = "2")
    private Integer quantidade;

    @ApiModelProperty(example = "75.25")
    private BigDecimal precoUnitario;

    @ApiModelProperty(example = "150.50")
    private BigDecimal precoTotal;

    @ApiModelProperty(example = "Pouco sal, por favor")
    private String observacao;

}