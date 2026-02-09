package com.test.msclientes.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.test.msclientes.config.RabbitConfig;

@Component
public class ClienteEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(ClienteEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;

    public ClienteEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishClienteUpserted(ClienteUpsertedEvent event) {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitConfig.CLIENTES_EXCHANGE,
                    RabbitConfig.CLIENTE_UPSERTED_KEY,
                    event
            );
        } catch (Exception ex) {
            log.warn("No se pudo publicar evento ClienteUpsertedEvent a RabbitMQ. Se continuará sin evento. Error: {}",
                    ex.getMessage());
        }
    }
}