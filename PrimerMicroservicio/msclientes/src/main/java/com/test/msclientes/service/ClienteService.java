package com.test.msclientes.service;

import java.util.List;

import com.test.msclientes.dto.ClienteRequest;
import com.test.msclientes.dto.ClienteResponse;

public interface ClienteService {
    public ClienteResponse crear(ClienteRequest request);

    public List<ClienteResponse> listar();

    public ClienteResponse obtenerPorClienteId(Long clienteId);

    public ClienteResponse actualizar(Long clienteId, ClienteRequest request);

    public void eliminar(Long clienteId);
}
