package com.course.springfood.listener;

import com.course.springfood.di.notificacao.NivelUrgencia;
import com.course.springfood.di.notificacao.Notificador;
import com.course.springfood.di.notificacao.TipoDoNotificador;
import com.course.springfood.di.service.ClienteAtivadoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoService {

    @TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
    @Autowired
    private Notificador notificador;

    @EventListener
    public void clienteAtivadoListener(ClienteAtivadoEvent event) {
        notificador.notificar(event.getCliente(), "Seu cadastro no sistema está ativo!");
    }

}