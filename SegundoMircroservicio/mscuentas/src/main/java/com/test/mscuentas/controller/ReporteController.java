package com.test.mscuentas.controller;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.mscuentas.dto.ReporteEstadoCuentaResponse;
import com.test.mscuentas.exception.BadRequestException;
import com.test.mscuentas.service.ReporteService;

@RestController
@RequestMapping("/reportes")
public class ReporteController {
    private final ReporteService service;

    public ReporteController(ReporteService service) {
        this.service = service;
    }

    
    @GetMapping
    public ReporteEstadoCuentaResponse estadoCuenta(
            @RequestParam("fecha") String fechaRango,
            @RequestParam("clienteId") Long clienteId
    ) {

        String[] parts = fechaRango.split(",");
        if (parts.length != 2) {
            throw new BadRequestException("Parámetro 'fecha' inválido. Use: YYYY-MM-DD,YYYY-MM-DD");
        }

        LocalDate desde = LocalDate.parse(parts[0].trim());
        LocalDate hasta = LocalDate.parse(parts[1].trim());

        if (hasta.isBefore(desde)) {
            throw new BadRequestException("Rango de fechas inválido: hasta < desde");
        }

        return service.generarEstadoCuenta(clienteId, desde, hasta);
    }
}
