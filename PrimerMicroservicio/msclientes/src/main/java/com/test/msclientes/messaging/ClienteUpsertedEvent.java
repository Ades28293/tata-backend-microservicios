package com.test.msclientes.messaging;

public record ClienteUpsertedEvent(
        Long clienteId,
        String identificacion,
        String nombre,
        Boolean estado
) {}