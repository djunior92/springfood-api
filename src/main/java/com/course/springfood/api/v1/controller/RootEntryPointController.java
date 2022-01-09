package com.course.springfood.api.v1.controller;

import com.course.springfood.api.v1.SpringLinks;
import com.course.springfood.core.security.SpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(path = "/v1")
public class RootEntryPointController {

    @Autowired
    private SpringLinks springLinks;

    @Autowired
    private SpringSecurity springSecurity;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

        if (springSecurity.podeConsultarCozinhas()) {
            rootEntryPointModel.add(springLinks.linkToCozinhas("cozinhas"));
        }

        if (springSecurity.podePesquisarPedidos()) {
            rootEntryPointModel.add(springLinks.linkToPedidos("pedidos"));
        }

        if (springSecurity.podeConsultarRestaurantes()) {
            rootEntryPointModel.add(springLinks.linkToRestaurantes("restaurantes"));
        }

        if (springSecurity.podeConsultarUsuariosGruposPermissoes()) {
            rootEntryPointModel.add(springLinks.linkToGrupos("grupos"));
            rootEntryPointModel.add(springLinks.linkToUsuarios("usuarios"));
            rootEntryPointModel.add(springLinks.linkToPermissoes("permissoes"));
        }

        if (springSecurity.podeConsultarFormasPagamento()) {
            rootEntryPointModel.add(springLinks.linkToFormasPagamento("formas-pagamento"));
        }

        if (springSecurity.podeConsultarEstados()) {
            rootEntryPointModel.add(springLinks.linkToEstados("estados"));
        }

        if (springSecurity.podeConsultarCidades()) {
            rootEntryPointModel.add(springLinks.linkToCidades("cidades"));
        }

        if (springSecurity.podeConsultarEstatisticas()) {
            rootEntryPointModel.add(springLinks.linkToEstatisticas("estatisticas"));
        }

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }

}