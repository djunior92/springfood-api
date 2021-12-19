package com.course.springfood.api.v2.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class CozinhaInputV2 {

    @ApiModelProperty(example = "Tailandesa", required = true)
    @NotBlank
    private String nomeCozinha;

}