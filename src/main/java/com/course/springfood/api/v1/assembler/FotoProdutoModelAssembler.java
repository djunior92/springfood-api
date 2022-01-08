package com.course.springfood.api.v1.assembler;

import com.course.springfood.api.v1.SpringLinks;
import com.course.springfood.api.v1.controller.RestauranteProdutoFotoController;
import com.course.springfood.api.v1.model.FotoProdutoModel;
import com.course.springfood.core.security.SpringSecurity;
import com.course.springfood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoModelAssembler
        extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SpringLinks springLinks;

    @Autowired
    private SpringSecurity springSecurity;

    public FotoProdutoModelAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoModel.class);
    }

    @Override
    public FotoProdutoModel toModel(FotoProduto foto) {
        FotoProdutoModel fotoProdutoModel = modelMapper.map(foto, FotoProdutoModel.class);

        // Quem pode consultar restaurantes, também pode consultar os produtos e fotos
        if (springSecurity.podeConsultarRestaurantes()) {
            fotoProdutoModel.add(springLinks.linkToFotoProduto(
                    foto.getRestauranteId(), foto.getProduto().getId()));

            fotoProdutoModel.add(springLinks.linkToProduto(
                    foto.getRestauranteId(), foto.getProduto().getId(), "produto"));
        }

        return fotoProdutoModel;
    }

}