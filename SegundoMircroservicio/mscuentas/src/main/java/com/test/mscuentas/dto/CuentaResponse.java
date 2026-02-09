package com.test.mscuentas.dto;

import java.math.BigDecimal;

public record CuentaResponse(
        Long cuentaId,
        String numeroCuenta,
        String tipoCuenta,
        BigDecimal saldoDisponible,
        Boolean estado,
        Long clienteId) {
}