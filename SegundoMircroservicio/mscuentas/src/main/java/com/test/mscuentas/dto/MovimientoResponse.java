package com.test.mscuentas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoResponse(
        Long movimientoId,
        LocalDateTime fecha,
        String tipoMovimiento,
        BigDecimal valor,
        BigDecimal saldo,
        Long cuentaId
) {}