package com.course.springfood.api.v2.assembler;

import com.course.springfood.api.v2.SpringLinksV2;
import com.course.springfood.api.v2.controller.CozinhaControllerV2;
import com.course.springfood.api.v2.model.CozinhaModelV2;
import com.course.springfood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaModelAssemblerV2
        extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModelV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SpringLinksV2 springLinks;

    public CozinhaModelAssemblerV2() {
        super(CozinhaControllerV2.class, CozinhaModelV2.class);
    }

    @Override
    public CozinhaModelV2 toModel(Cozinha cozinha) {
        CozinhaModelV2 cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);

        cozinhaModel.add(springLinks.linkToCozinhas("cozinhas"));

        return cozinhaModel;
    }

}