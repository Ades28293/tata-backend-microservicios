package com.test.mscuentas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MovimientoRequest(
        @NotNull Long cuentaId,
        @NotNull LocalDate fecha,          
        @NotBlank String tipoMovimiento,
        @NotNull BigDecimal valor
) {
}