package com.course.springfood.di.service;

import com.course.springfood.di.modelo.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

//@Component
public class AtivacaoClienteService {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    //	@PostConstruct
    public void init() {
        System.out.println("INIT " + notificador);
    }

    //	@PreDestroy
    public void destroy() {
        System.out.println("DESTROY");
    }

    public void ativar(Cliente cliente) {
        cliente.ativar();

        eventPublisher.publishEvent(new ClienteAtivadoEvent(cliente));
    }

}