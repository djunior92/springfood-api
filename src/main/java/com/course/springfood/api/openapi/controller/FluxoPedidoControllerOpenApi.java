package com.course.springfood.api.openapi.controller;

import com.course.springfood.api.exceptionhandler.Problem;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

@Api(tags = "Pedidos")
public interface FluxoPedidoControllerOpenApi {

    @ApiOperation("Confirmação de pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido confirmado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> confirmar(
            @ApiParam(value = "Código do pedido", example = "2a956b96-1e6c-474a-adb2-a07111a03152",
                    required = true)
                    String codigoPedido);

    @ApiOperation("Cancelamento de pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido cancelado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> cancelar(
            @ApiParam(value = "Código do pedido", example = "2a956b96-1e6c-474a-adb2-a07111a03152",
                    required = true)
                    String codigoPedido);

    @ApiOperation("Registrar entrega de pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Entrega de pedido registrada com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> entregar(
            @ApiParam(value = "Código do pedido", example = "2a956b96-1e6c-474a-adb2-a07111a03152",
                    required = true)
                    String codigoPedido);

}