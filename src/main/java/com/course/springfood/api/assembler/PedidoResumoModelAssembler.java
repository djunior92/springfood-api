package com.course.springfood.api.assembler;

import com.course.springfood.api.SpringLinks;
import com.course.springfood.api.controller.PedidoController;
import com.course.springfood.api.model.PedidoResumoModel;
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

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);

        pedidoModel.add(springLinks.linkToPedidos("pedidos"));

        pedidoModel.getRestaurante().add(
                springLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoModel.getCliente().add(springLinks.linkToUsuario(pedido.getCliente().getId()));

        return pedidoModel;
    }

}