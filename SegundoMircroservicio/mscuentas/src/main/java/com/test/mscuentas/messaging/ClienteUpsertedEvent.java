package com.test.mscuentas.messaging;

public record ClienteUpsertedEvent(
        Long clienteId,
        String identificacion,
        String nombre,
        Boolean estado
) {}