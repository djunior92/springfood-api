package com.course.springfood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class EnderecoInput {

    @ApiModelProperty(example = "15800-000", required = true)
    @NotBlank
    private String cep;

    @ApiModelProperty(example = "Rua Brasil", required = true)
    @NotBlank
    private String logradouro;

    @ApiModelProperty(example = "\"100\"", required = true)
    @NotBlank
    private String numero;

    @ApiModelProperty(example = "Apto 1050")
    private String complemento;

    @ApiModelProperty(example = "Centro", required = true)
    @NotBlank
    private String bairro;

    @Valid
    @NotNull
    private CidadeIdInput cidade;

}