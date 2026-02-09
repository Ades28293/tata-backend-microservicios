package com.test.mscuentas.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CuentaRequest(
        @NotBlank @Size(max = 30) String numeroCuenta,
        @NotBlank @Size(max = 20) String tipoCuenta,
        @NotNull @DecimalMin("0.00") BigDecimal saldoInicial,
        @NotNull Boolean estado,
        @NotNull Long clienteId) {
}