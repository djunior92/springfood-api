package com.course.springfood.api.v1.controller;

import com.course.springfood.api.v1.SpringLinks;
import com.course.springfood.api.v1.assembler.PermissaoModelAssembler;
import com.course.springfood.api.v1.model.PermissaoModel;
import com.course.springfood.api.v1.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.course.springfood.core.security.CheckSecurity;
import com.course.springfood.core.security.SpringSecurity;
import com.course.springfood.domain.model.Grupo;
import com.course.springfood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;

    @Autowired
    private SpringLinks springLinks;

    @Autowired
    private SpringSecurity springSecurity;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);

        CollectionModel<PermissaoModel> permissoesModel
                = permissaoModelAssembler.toCollectionModel(grupo.getPermissoes())
                .removeLinks();

        permissoesModel.add(springLinks.linkToGrupoPermissoes(grupoId));

        if (springSecurity.podeEditarUsuariosGruposPermissoes()) {
            permissoesModel.add(springLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));

            permissoesModel.getContent().forEach(permissaoModel -> {
                permissaoModel.add(springLinks.linkToGrupoPermissaoDesassociacao(
                        grupoId, permissaoModel.getId(), "desassociar"));
            });
        }

        return permissoesModel;
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @DeleteMapping(value = "/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.desassociarPermissao(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @PutMapping(value = "/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.associarPermissao(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }

}