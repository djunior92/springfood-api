package com.course.springfood;

import com.course.springfood.di.notificacao.NotificadorEmail;
import com.course.springfood.di.service.AtivacaoClienteService;
import org.springframework.context.annotation.Bean;

//@Configuration
public class FoodConfig {

    @Bean
    public NotificadorEmail notificadorEmail() {
        NotificadorEmail notificador = new NotificadorEmail("smtp.algamail.com.br");
        notificador.setCaixaAlta(true);

        return notificador;
    }

    @Bean
    public AtivacaoClienteService ativacaoClienteService() {
        return new AtivacaoClienteService(notificadorEmail());
    }

}