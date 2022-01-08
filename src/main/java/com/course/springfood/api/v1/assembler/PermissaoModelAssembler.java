package com.course.springfood.api.v1.assembler;

import com.course.springfood.api.v1.SpringLinks;
import com.course.springfood.api.v1.model.PermissaoModel;
import com.course.springfood.core.security.SpringSecurity;
import com.course.springfood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PermissaoModelAssembler
        implements RepresentationModelAssembler<Permissao, PermissaoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SpringLinks springLinks;

    @Autowired
    private SpringSecurity springSecurity;

    @Override
    public PermissaoModel toModel(Permissao permissao) {
        PermissaoModel permissaoModel = modelMapper.map(permissao, PermissaoModel.class);
        return permissaoModel;
    }

    @Override
    public CollectionModel<PermissaoModel> toCollectionModel(Iterable<? extends Permissao> entities) {
        CollectionModel<PermissaoModel> collectionModel
                = RepresentationModelAssembler.super.toCollectionModel(entities);

        if (springSecurity.podeConsultarUsuariosGruposPermissoes()) {
            collectionModel.add(springLinks.linkToPermissoes());
        }

        return collectionModel;
    }

}