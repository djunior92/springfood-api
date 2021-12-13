package com.course.springfood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Setter
@Getter
public class PedidoModel {

    @ApiModelProperty(example = "10304a8d-eb74-453d-b1ee-9158af497dfe")
    private String codigo;

    @ApiModelProperty(example = "250.50")
    private BigDecimal subtotal;

    @ApiModelProperty(example = "10.00")
    private BigDecimal taxaFrete;

    @ApiModelProperty(example = "260.50")
    private BigDecimal valorTotal;

    @ApiModelProperty(example = "CRIADO")
    private String status;

    @ApiModelProperty(example = "2021-12-12T22:35:04Z")
    private OffsetDateTime dataCriacao;

    @ApiModelProperty(example = "2021-12-12T22:36:04Z")
    private OffsetDateTime dataConfirmacao;

    @ApiModelProperty(example = "2021-12-12T22:55:34Z")
    private OffsetDateTime dataEntrega;

    @ApiModelProperty(example = "2021-12-12T22:36:00Z")
    private OffsetDateTime dataCancelamento;

    private RestauranteResumoModel restaurante;
    private UsuarioModel cliente;
    private FormaPagamentoModel formaPagamento;
    private EnderecoModel enderecoEntrega;
    private List<ItemPedidoModel> itens;

}