package com.course.springfood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Setter
@Getter
public class ProdutoInput {

    @ApiModelProperty(example = "Camarão", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "Porção de camarões", required = true)
    @NotBlank
    private String descricao;

    @ApiModelProperty(example = "110.00", required = true)
    @NotNull
    @PositiveOrZero
    private BigDecimal preco;

    @ApiModelProperty(example = "true", required = true)
    @NotNull
    private Boolean ativo;

}