package com.course.springfood.api.v1.assembler;

import com.course.springfood.api.v1.SpringLinks;
import com.course.springfood.api.v1.controller.PedidoController;
import com.course.springfood.api.v1.model.PedidoResumoModel;
import com.course.springfood.core.security.SpringSecurity;
import com.course.springfood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoModelAssembler
        extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SpringLinks springLinks;

    @Autowired
    private SpringSecurity springSecurity;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);

        if (springSecurity.podePesquisarPedidos()) {
            pedidoModel.add(springLinks.linkToPedidos("pedidos"));
        }

        if (springSecurity.podeConsultarRestaurantes()) {
            pedidoModel.getRestaurante().add(
                    springLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        }

        if (springSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoModel.getCliente().add(springLinks.linkToUsuario(pedido.getCliente().getId()));
        }

        return pedidoModel;
    }

}