package com.course.springfood.api.controller;

import com.course.springfood.api.SpringLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private SpringLinks springLinks;

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

        rootEntryPointModel.add(springLinks.linkToCozinhas("cozinhas"));
        rootEntryPointModel.add(springLinks.linkToPedidos("pedidos"));
        rootEntryPointModel.add(springLinks.linkToRestaurantes("restaurantes"));
        rootEntryPointModel.add(springLinks.linkToGrupos("grupos"));
        rootEntryPointModel.add(springLinks.linkToUsuarios("usuarios"));
        rootEntryPointModel.add(springLinks.linkToPermissoes("permissoes"));
        rootEntryPointModel.add(springLinks.linkToFormasPagamento("formas-pagamento"));
        rootEntryPointModel.add(springLinks.linkToEstados("estados"));
        rootEntryPointModel.add(springLinks.linkToCidades("cidades"));
        rootEntryPointModel.add(springLinks.linkToEstatisticas("estatisticas"));

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }

}