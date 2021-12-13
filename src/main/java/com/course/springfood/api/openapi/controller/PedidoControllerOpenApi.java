package com.course.springfood.api.openapi.controller;

import com.course.springfood.api.exceptionhandler.Problem;
import com.course.springfood.api.model.PedidoModel;
import com.course.springfood.api.model.PedidoResumoModel;
import com.course.springfood.api.model.input.PedidoInput;
import com.course.springfood.domain.filter.PedidoFilter;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

    @ApiOperation("Pesquisa os pedidos")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable);

    @ApiOperation("Registra um pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pedido registrado"),
    })
    PedidoModel adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo pedido", required = true)
                    PedidoInput pedidoInput);

    @ApiOperation("Busca um pedido por código")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    PedidoModel buscar(
            @ApiParam(value = "Código de um pedido", example = "2a956b96-1e6c-474a-adb2-a07111a03152",
                    required = true)
                    String codigoPedido);

}