package com.course.springfood.di.service;

import com.course.springfood.di.modelo.Cliente;
import com.course.springfood.di.notificacao.NivelUrgencia;
import com.course.springfood.di.notificacao.Notificador;
import com.course.springfood.di.notificacao.TipoDoNotificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AtivacaoClienteService {

    @TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
    @Autowired
    private Notificador notificador;

    public void ativar(Cliente cliente) {
        cliente.ativar();

        notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
    }

}
