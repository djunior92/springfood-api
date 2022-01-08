package com.course.springfood.api.v1.assembler;

import com.course.springfood.api.v1.SpringLinks;
import com.course.springfood.api.v1.controller.RestauranteController;
import com.course.springfood.api.v1.model.RestauranteModel;
import com.course.springfood.core.security.SpringSecurity;
import com.course.springfood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteModelAssembler
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SpringLinks springLinks;

    @Autowired
    private SpringSecurity springSecurity;

    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);

        if (springSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(springLinks.linkToRestaurantes("restaurantes"));
        }

        if (springSecurity.podeGerenciarCadastroRestaurantes()) {
            if (restaurante.ativacaoPermitida()) {
                restauranteModel.add(
                        springLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
            }

            if (restaurante.inativacaoPermitida()) {
                restauranteModel.add(
                        springLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
            }
        }

        if (springSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
            if (restaurante.aberturaPermitida()) {
                restauranteModel.add(
                        springLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
            }

            if (restaurante.fechamentoPermitido()) {
                restauranteModel.add(
                        springLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
            }
        }

        if (springSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(springLinks.linkToProdutos(restaurante.getId(), "produtos"));
        }

        if (springSecurity.podeConsultarCozinhas()) {
            restauranteModel.getCozinha().add(
                    springLinks.linkToCozinha(restaurante.getCozinha().getId()));
        }

        if (springSecurity.podeConsultarCidades()) {
            if (restauranteModel.getEndereco() != null
                    && restauranteModel.getEndereco().getCidade() != null) {
                restauranteModel.getEndereco().getCidade().add(
                        springLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
            }
        }

        if (springSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(springLinks.linkToRestauranteFormasPagamento(restaurante.getId(),
                    "formas-pagamento"));
        }

        if (springSecurity.podeGerenciarCadastroRestaurantes()) {
            restauranteModel.add(springLinks.linkToRestauranteResponsaveis(restaurante.getId(),
                    "responsaveis"));
        }

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteModel> collectionModel = super.toCollectionModel(entities);

        if (springSecurity.podeConsultarRestaurantes()) {
            collectionModel.add(springLinks.linkToRestaurantes());
        }

        return collectionModel;
    }

}