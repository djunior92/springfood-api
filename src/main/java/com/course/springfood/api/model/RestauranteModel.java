package com.course.springfood.api.model;

import com.course.springfood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteModel {

    @ApiModelProperty(example = "1")
    @JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
    private Long id;

    @ApiModelProperty(example = "Gourmet")
    @JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
    private String nome;

    @ApiModelProperty(example = "10.00")
    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal taxaFrete;

    @JsonView(RestauranteView.Resumo.class)
    private CozinhaModel cozinha;

    private Boolean ativo;
    private Boolean aberto;
    private EnderecoModel endereco;

}