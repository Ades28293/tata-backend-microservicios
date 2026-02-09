package com.test.mscuentas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ReporteEstadoCuentaResponse(
        Long clienteId,
        String clienteNombre,
        String clienteIdentificacion,
        List<CuentaReporte> cuentas
) {
    public record CuentaReporte(
            Long cuentaId,
            String numeroCuenta,
            String tipoCuenta,
            BigDecimal saldoDisponible,
            List<MovimientoReporte> movimientos
    ) {}

    public record MovimientoReporte(
            Long movimientoId,
            LocalDateTime fecha,
            String tipoMovimiento,
            BigDecimal valor,
            BigDecimal saldo
    ) {}
}