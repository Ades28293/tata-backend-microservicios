package com.test.mscuentas.service;

import java.time.LocalDate;

import com.test.mscuentas.dto.ReporteEstadoCuentaResponse;

public interface ReporteService {

    public ReporteEstadoCuentaResponse generarEstadoCuenta(Long clienteId, LocalDate desde, LocalDate hasta);
}
