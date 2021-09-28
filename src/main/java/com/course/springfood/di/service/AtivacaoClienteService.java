package com.course.springfood.di.service;

import com.course.springfood.di.modelo.Cliente;
import com.course.springfood.di.notificacao.Notificador;
import com.course.springfood.di.notificacao.NotificadorEmail;
import org.springframework.stereotype.Component;

public class AtivacaoClienteService {

    private Notificador notificador;

    public AtivacaoClienteService(Notificador notificador) {
        this.notificador = notificador;

        System.out.println("AtivacaoClienteService: " + notificador);
    }

    public void ativar(Cliente cliente) {
        cliente.ativar();

        notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
    }

}