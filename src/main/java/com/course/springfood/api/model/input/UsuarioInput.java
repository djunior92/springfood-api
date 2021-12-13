package com.course.springfood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class UsuarioInput {

    @ApiModelProperty(example = "José da Silva", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "jose.silva@springfood.com", required = true)
    @NotBlank
    @Email
    private String email;

}