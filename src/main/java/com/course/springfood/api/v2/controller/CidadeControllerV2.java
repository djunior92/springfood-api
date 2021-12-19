package com.course.springfood.api.v2.controller;

import com.course.springfood.api.ResourceUriHelper;
import com.course.springfood.api.v2.assembler.CidadeInputDisassemblerV2;
import com.course.springfood.api.v2.assembler.CidadeModelAssemblerV2;
import com.course.springfood.api.v2.model.CidadeModelV2;
import com.course.springfood.api.v2.model.input.CidadeInputV2;
import com.course.springfood.api.v2.openapi.CidadeControllerV2OpenApi;
import com.course.springfood.domain.exception.EstadoNaoEncontradoException;
import com.course.springfood.domain.exception.NegocioException;
import com.course.springfood.domain.model.Cidade;
import com.course.springfood.domain.repository.CidadeRepository;
import com.course.springfood.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v2/cidades")
public class CidadeControllerV2 implements CidadeControllerV2OpenApi {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CidadeModelAssemblerV2 cidadeModelAssembler;

    @Autowired
    private CidadeInputDisassemblerV2 cidadeInputDisassembler;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<CidadeModelV2> listar() {
        List<Cidade> todasCidades = cidadeRepository.findAll();

        return cidadeModelAssembler.toCollectionModel(todasCidades);
    }

    @Override
    @GetMapping(value = "/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeModelV2 buscar(@PathVariable Long cidadeId) {
        Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);

        return cidadeModelAssembler.toModel(cidade);
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModelV2 adicionar(@RequestBody @Valid CidadeInputV2 cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

            cidade = cadastroCidade.salvar(cidade);

            CidadeModelV2 cidadeModel = cidadeModelAssembler.toModel(cidade);

            ResourceUriHelper.addUriInResponseHeader(cidadeModel.getIdCidade());

            return cidadeModel;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @PutMapping(value = "/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeModelV2 atualizar(@PathVariable Long cidadeId,
                                   @RequestBody @Valid CidadeInputV2 cidadeInput) {
        try {
            Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

            cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

            cidadeAtual = cadastroCidade.salvar(cidadeAtual);

            return cidadeModelAssembler.toModel(cidadeAtual);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cadastroCidade.excluir(cidadeId);
    }

}