package com.test.mscuentas.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.test.mscuentas.config.RabbitConfig;
import com.test.mscuentas.model.ClienteRef;
import com.test.mscuentas.repository.ClienteRefRepository;

@Component
public class ClienteEventListener {
        private final ClienteRefRepository repo;

    public ClienteEventListener(ClienteRefRepository repo) {
        this.repo = repo;
    }

    @RabbitListener(queues = RabbitConfig.CLIENTE_UPSERTED_QUEUE)
    public void onClienteUpserted(ClienteUpsertedEvent event) {

        ClienteRef ref = new ClienteRef();
        ref.setClienteId(event.clienteId());
        ref.setIdentificacion(event.identificacion());
        ref.setNombre(event.nombre());
        ref.setEstado(event.estado());

        repo.save(ref);
    }
}
