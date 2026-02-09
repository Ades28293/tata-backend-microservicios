package com.test.mscuentas.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String CLIENTES_EXCHANGE = "clientes.exchange";
    public static final String CLIENTE_UPSERTED_KEY = "clientes.upserted";
    public static final String CLIENTE_UPSERTED_QUEUE = "clientes.upserted.queue";

    @Bean
    DirectExchange clientesExchange() {
        return new DirectExchange(CLIENTES_EXCHANGE);
    }

    @Bean
    Queue clienteUpsertedQueue() {
        return QueueBuilder.durable(CLIENTE_UPSERTED_QUEUE).build();
    }

    @Bean
    Binding clienteUpsertedBinding(Queue clienteUpsertedQueue, DirectExchange clientesExchange) {
        return BindingBuilder
                .bind(clienteUpsertedQueue)
                .to(clientesExchange)
                .with(CLIENTE_UPSERTED_KEY);
    }

    
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();

        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages(
                "com.test.msclientes.messaging",
                "com.test.mscuentas.messaging",
                "java.util"
        );

        Map<String, Class<?>> mapping = new HashMap<>();
        mapping.put(
                "com.test.msclientes.messaging.ClienteUpsertedEvent",
                com.test.mscuentas.messaging.ClienteUpsertedEvent.class
        );
        classMapper.setIdClassMapping(mapping);

        converter.setClassMapper(classMapper);
        return converter;
    }

  
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter jackson2JsonMessageConverter
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter);
        return factory;
    }
}
