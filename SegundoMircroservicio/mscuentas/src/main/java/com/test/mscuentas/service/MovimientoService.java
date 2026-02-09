package com.test.mscuentas.service;

import java.util.List;

import com.test.mscuentas.dto.MovimientoRequest;
import com.test.mscuentas.dto.MovimientoResponse;

public interface MovimientoService {

    public MovimientoResponse registrar(MovimientoRequest request);

    public List<MovimientoResponse> listar();

    public List<MovimientoResponse> listarPorCuenta(Long cuentaId);
}
