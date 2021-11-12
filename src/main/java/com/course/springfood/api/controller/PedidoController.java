package com.course.springfood.api.controller;

import com.course.springfood.api.assembler.PedidoInputDisassembler;
import com.course.springfood.api.assembler.PedidoModelAssembler;
import com.course.springfood.api.assembler.PedidoResumoModelAssembler;
import com.course.springfood.api.model.PedidoModel;
import com.course.springfood.api.model.PedidoResumoModel;
import com.course.springfood.api.model.input.PedidoInput;
import com.course.springfood.domain.exception.EntidadeNaoEncontradaException;
import com.course.springfood.domain.exception.NegocioException;
import com.course.springfood.domain.model.Pedido;
import com.course.springfood.domain.model.Usuario;
import com.course.springfood.domain.repository.PedidoRepository;
import com.course.springfood.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;

    @GetMapping
    public List<PedidoResumoModel> listar() {
        List<Pedido> todosPedidos = pedidoRepository.findAll();

        return pedidoResumoModelAssembler.toCollectionModel(todosPedidos);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedido.emitir(novoPedido);

            return pedidoModelAssembler.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @GetMapping("/{pedidoId}")
    public PedidoModel buscar(@PathVariable Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        return pedidoModelAssembler.toModel(pedido);
    }

}