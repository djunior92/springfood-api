package com.course.springfood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cozinhas")
@Setter
@Getter
public class CozinhaModel extends RepresentationModel<CozinhaModel> {

    @ApiModelProperty(example = "1")
//	@JsonView(RestauranteView.Resumo.class)
    private Long id;

    @ApiModelProperty(example = "Tailandesa")
//	@JsonView(RestauranteView.Resumo.class)
    private String nome;

}