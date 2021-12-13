package com.course.springfood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class CozinhaInput {

    @ApiModelProperty(example = "Tailandesa", required = true)
    @NotBlank
    private String nome;

}