package com.course.springfood.di.service;

import com.course.springfood.di.modelo.Cliente;

public class ClienteAtivadoEvent {

    private Cliente cliente;

    public ClienteAtivadoEvent(Cliente cliente) {
        super();
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

}
