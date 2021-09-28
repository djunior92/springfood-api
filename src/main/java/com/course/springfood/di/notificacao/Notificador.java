package com.course.springfood.di.notificacao;

import com.course.springfood.di.modelo.Cliente;

public interface Notificador {

    void notificar(Cliente cliente, String mensagem);

}