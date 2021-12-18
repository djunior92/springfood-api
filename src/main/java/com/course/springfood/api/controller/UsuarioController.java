package com.course.springfood.api.controller;

import com.course.springfood.api.assembler.UsuarioInputDisassembler;
import com.course.springfood.api.assembler.UsuarioModelAssembler;
import com.course.springfood.api.model.UsuarioModel;
import com.course.springfood.api.model.input.SenhaInput;
import com.course.springfood.api.model.input.UsuarioComSenhaInput;
import com.course.springfood.api.model.input.UsuarioInput;
import com.course.springfood.api.openapi.controller.UsuarioControllerOpenApi;
import com.course.springfood.domain.model.Usuario;
import com.course.springfood.domain.repository.UsuarioRepository;
import com.course.springfood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController implements UsuarioControllerOpenApi {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<UsuarioModel> listar() {
        List<Usuario> todasUsuarios = usuarioRepository.findAll();

        return usuarioModelAssembler.toCollectionModel(todasUsuarios);
    }

    @GetMapping(value = "/{usuarioId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioModel buscar(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

        return usuarioModelAssembler.toModel(usuario);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
        usuario = cadastroUsuario.salvar(usuario);

        return usuarioModelAssembler.toModel(usuario);
    }

    @PutMapping(value = "/{usuarioId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioModel atualizar(@PathVariable Long usuarioId,
                                  @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);
        usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
        usuarioAtual = cadastroUsuario.salvar(usuarioAtual);

        return usuarioModelAssembler.toModel(usuarioAtual);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
        cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
    }

}