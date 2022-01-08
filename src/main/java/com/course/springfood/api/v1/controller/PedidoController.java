package com.course.springfood.api.v1.controller;

import com.course.springfood.api.v1.assembler.PedidoInputDisassembler;
import com.course.springfood.api.v1.assembler.PedidoModelAssembler;
import com.course.springfood.api.v1.assembler.PedidoResumoModelAssembler;
import com.course.springfood.api.v1.model.PedidoModel;
import com.course.springfood.api.v1.model.PedidoResumoModel;
import com.course.springfood.api.v1.model.input.PedidoInput;
import com.course.springfood.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.course.springfood.core.data.PageWrapper;
import com.course.springfood.core.data.PageableTranslator;
import com.course.springfood.core.security.CheckSecurity;
import com.course.springfood.core.security.SpringSecurity;
import com.course.springfood.domain.exception.EntidadeNaoEncontradaException;
import com.course.springfood.domain.exception.NegocioException;
import com.course.springfood.domain.filter.PedidoFilter;
import com.course.springfood.domain.model.Pedido;
import com.course.springfood.domain.model.Usuario;
import com.course.springfood.domain.repository.PedidoRepository;
import com.course.springfood.domain.service.EmissaoPedidoService;
import com.course.springfood.infrastructure.repository.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(path = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

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

    @Autowired
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    @Autowired
    private SpringSecurity springSecurity;

    @CheckSecurity.Pedidos.PodePesquisar
    @Override
    @GetMapping
    public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro,
                                                   @PageableDefault(size = 10) Pageable pageable) {
        Pageable pageableTraduzido = traduzirPageable(pageable);

        Page<Pedido> pedidosPage = pedidoRepository.findAll(
                PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);

        pedidosPage = new PageWrapper<>(pedidosPage, pageable);

        return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
    }

    @CheckSecurity.Pedidos.PodeCriar
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(springSecurity.getUsuarioId());

            novoPedido = emissaoPedido.emitir(novoPedido);

            return pedidoModelAssembler.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Pedidos.PodeBuscar
    @Override
    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable String codigoPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

        return pedidoModelAssembler.toModel(pedido);
    }

    private Pageable traduzirPageable(Pageable apiPageable) {
        var mapeamento = Map.of(
                "codigo", "codigo",
                "subtotal", "subtotal",
                "taxaFrete", "taxaFrete",
                "valorTotal", "valorTotal",
                "dataCriacao", "dataCriacao",
                "restaurante.nome", "restaurante.nome",
                "restaurante.id", "restaurante.id",
                "cliente.id", "cliente.id",
                "cliente.nome", "cliente.nome"
        );

        return PageableTranslator.translate(apiPageable, mapeamento);
    }

}