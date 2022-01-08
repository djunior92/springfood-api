package com.course.springfood.api.v1.assembler;

import com.course.springfood.api.v1.SpringLinks;
import com.course.springfood.api.v1.controller.PedidoController;
import com.course.springfood.api.v1.model.PedidoModel;
import com.course.springfood.core.security.SpringSecurity;
import com.course.springfood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelAssembler
        extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SpringLinks springLinks;

    @Autowired
    private SpringSecurity springSecurity;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);

        if (springSecurity.podePesquisarPedidos()) {
            pedidoModel.add(springLinks.linkToPedidos("pedidos"));
        }

        if (springSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
            if (pedido.podeSerConfirmado()) {
                pedidoModel.add(springLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
            }

            if (pedido.podeSerCancelado()) {
                pedidoModel.add(springLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
            }

            if (pedido.podeSerEntregue()) {
                pedidoModel.add(springLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
            }
        }

        if (springSecurity.podeConsultarRestaurantes()) {
            pedidoModel.getRestaurante().add(
                    springLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        }

        if (springSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoModel.getCliente().add(
                    springLinks.linkToUsuario(pedido.getCliente().getId()));
        }

        if (springSecurity.podeConsultarFormasPagamento()) {
            pedidoModel.getFormaPagamento().add(
                    springLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
        }

        if (springSecurity.podeConsultarCidades()) {
            pedidoModel.getEnderecoEntrega().getCidade().add(
                    springLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
        }

        // Quem pode consultar restaurantes, também pode consultar os produtos dos restaurantes
        if (springSecurity.podeConsultarRestaurantes()) {
            pedidoModel.getItens().forEach(item -> {
                item.add(springLinks.linkToProduto(
                        pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
            });
        }

        return pedidoModel;
    }

}