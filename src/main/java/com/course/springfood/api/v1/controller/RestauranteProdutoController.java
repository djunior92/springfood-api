package com.course.springfood.api.v1.controller;

import com.course.springfood.api.v1.SpringLinks;
import com.course.springfood.api.v1.assembler.ProdutoInputDisassembler;
import com.course.springfood.api.v1.assembler.ProdutoModelAssembler;
import com.course.springfood.api.v1.model.ProdutoModel;
import com.course.springfood.api.v1.model.input.ProdutoInput;
import com.course.springfood.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.course.springfood.core.security.CheckSecurity;
import com.course.springfood.domain.model.Produto;
import com.course.springfood.domain.model.Restaurante;
import com.course.springfood.domain.repository.ProdutoRepository;
import com.course.springfood.domain.service.CadastroProdutoService;
import com.course.springfood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private ProdutoModelAssembler produtoModelAssembler;

    @Autowired
    private ProdutoInputDisassembler produtoInputDisassembler;

    @Autowired
    private SpringLinks springLinks;

    @CheckSecurity.Restaurantes.PodeConsultar
    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<ProdutoModel> listar(@PathVariable Long restauranteId,
                                                @RequestParam(required = false, defaultValue = "false") Boolean incluirInativos) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        List<Produto> todosProdutos = null;

        if (incluirInativos) {
            todosProdutos = produtoRepository.findTodosByRestaurante(restaurante);
        } else {
            todosProdutos = produtoRepository.findAtivosByRestaurante(restaurante);
        }

        return produtoModelAssembler.toCollectionModel(todosProdutos)
                .add(springLinks.linkToProdutos(restauranteId));
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @Override
    @GetMapping(value = "/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

        return produtoModelAssembler.toModel(produto);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel adicionar(@PathVariable Long restauranteId,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        produto = cadastroProduto.salvar(produto);

        return produtoModelAssembler.toModel(produto);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Override
    @PutMapping(value = "/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);

        produtoAtual = cadastroProduto.salvar(produtoAtual);

        return produtoModelAssembler.toModel(produtoAtual);
    }

}