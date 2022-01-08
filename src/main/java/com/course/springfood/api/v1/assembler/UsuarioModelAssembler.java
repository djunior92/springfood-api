package com.course.springfood.api.v1.assembler;

import com.course.springfood.api.v1.SpringLinks;
import com.course.springfood.api.v1.controller.UsuarioController;
import com.course.springfood.api.v1.model.UsuarioModel;
import com.course.springfood.core.security.SpringSecurity;
import com.course.springfood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelAssembler
        extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SpringLinks springLinks;

    @Autowired
    private SpringSecurity springSecurity;

    public UsuarioModelAssembler() {
        super(UsuarioController.class, UsuarioModel.class);
    }

    @Override
    public UsuarioModel toModel(Usuario usuario) {
        UsuarioModel usuarioModel = createModelWithId(usuario.getId(), usuario);
        modelMapper.map(usuario, usuarioModel);

        if (springSecurity.podeConsultarUsuariosGruposPermissoes()) {
            usuarioModel.add(springLinks.linkToUsuarios("usuarios"));

            usuarioModel.add(springLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));
        }

        return usuarioModel;
    }

    @Override
    public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
        CollectionModel<UsuarioModel> collectionModel = super.toCollectionModel(entities);

        if (springSecurity.podeConsultarUsuariosGruposPermissoes()) {
            collectionModel.add(springLinks.linkToUsuarios());
        }

        return collectionModel;
    }

}