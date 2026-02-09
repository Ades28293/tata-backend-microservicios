package com.test.mscuentas.service;

import java.util.List;

import com.test.mscuentas.dto.CuentaRequest;
import com.test.mscuentas.dto.CuentaResponse;

public interface CuentaService {
    public CuentaResponse crear(CuentaRequest request);

    public List<CuentaResponse> listar();

    public CuentaResponse obtenerPorId(Long cuentaId);

    public List<CuentaResponse> listarPorCliente(Long clienteId);

    public CuentaResponse actualizar(Long cuentaId, CuentaRequest request);

    public void eliminar(Long cuentaId);
}
