package com.course.springfood.api.assembler;

import com.course.springfood.api.SpringLinks;
import com.course.springfood.api.controller.CidadeController;
import com.course.springfood.api.model.CidadeModel;
import com.course.springfood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeModelAssembler
        extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SpringLinks springLinks;

    public CidadeModelAssembler() {
        super(CidadeController.class, CidadeModel.class);
    }

    @Override
    public CidadeModel toModel(Cidade cidade) {
        CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);

        modelMapper.map(cidade, cidadeModel);

        cidadeModel.add(springLinks.linkToCidades("cidades"));

        cidadeModel.getEstado().add(springLinks.linkToEstado(cidadeModel.getEstado().getId()));

        return cidadeModel;
    }

    @Override
    public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(springLinks.linkToCidades());
    }

}