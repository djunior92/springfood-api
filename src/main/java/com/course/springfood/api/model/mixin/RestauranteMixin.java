package com.course.springfood.api.model.mixin;

import com.course.springfood.domain.model.Cozinha;
import com.course.springfood.domain.model.Endereco;
import com.course.springfood.domain.model.FormaPagamento;
import com.course.springfood.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

public abstract class RestauranteMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Cozinha cozinha;

    @JsonIgnore
    private Endereco endereco;

    @JsonIgnore
    private OffsetDateTime dataCadastro;

    @JsonIgnore
    private OffsetDateTime dataAtualizacao;

    @JsonIgnore
    private List<FormaPagamento> formasPagamento;

    @JsonIgnore
    private List<Produto> produtos;

}