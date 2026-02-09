package com.test.mscuentas.controller;

import com.test.mscuentas.dto.MovimientoRequest;
import com.test.mscuentas.dto.MovimientoResponse;
import com.test.mscuentas.service.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoService service;

    public MovimientoController(MovimientoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoResponse registrar(@Valid @RequestBody MovimientoRequest request) {
        return service.registrar(request);
    }

    @GetMapping
    public List<MovimientoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/cuenta/{cuentaId}")
    public List<MovimientoResponse> listarPorCuenta(@PathVariable Long cuentaId) {
        return service.listarPorCuenta(cuentaId);
    }
}