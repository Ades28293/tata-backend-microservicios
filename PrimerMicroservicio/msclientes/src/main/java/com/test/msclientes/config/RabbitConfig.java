package com.test.msclientes.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
     public static final String CLIENTES_EXCHANGE = "clientes.exchange";
    public static final String CLIENTE_UPSERTED_KEY = "clientes.upserted";

    @Bean
    DirectExchange clientesExchange() {
        return new DirectExchange(CLIENTES_EXCHANGE);
    }
}
