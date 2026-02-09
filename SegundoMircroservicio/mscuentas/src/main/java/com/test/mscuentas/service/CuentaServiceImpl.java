package com.test.mscuentas.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.test.mscuentas.exception.*;
import com.test.mscuentas.dto.CuentaRequest;
import com.test.mscuentas.dto.CuentaResponse;
import com.test.mscuentas.model.Cuenta;
import com.test.mscuentas.repository.CuentaRepository;

@Service
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository repo;

    public CuentaServiceImpl(CuentaRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public CuentaResponse crear(CuentaRequest r) {

        if (repo.existsByNumeroCuenta(r.numeroCuenta())) {
            throw new BadRequestException("Ya existe una cuenta con número " + r.numeroCuenta());
        }

        Cuenta c = new Cuenta();
        c.setNumeroCuenta(r.numeroCuenta());
        c.setTipoCuenta(r.tipoCuenta());
        c.setSaldoInicial(r.saldoInicial());
        c.setSaldoDisponible(r.saldoInicial()); 
        c.setEstado(r.estado());
        c.setClienteId(r.clienteId());

        return toResponse(repo.save(c));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuentaResponse> listar() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CuentaResponse obtenerPorId(Long cuentaId) {
        Cuenta c = repo.findById(cuentaId)
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));
        return toResponse(c);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuentaResponse> listarPorCliente(Long clienteId) {
        return repo.findByClienteId(clienteId).stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public CuentaResponse actualizar(Long cuentaId, CuentaRequest r) {

        Cuenta c = repo.findById(cuentaId)
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));

        c.setTipoCuenta(r.tipoCuenta());
        c.setEstado(r.estado());

        return toResponse(repo.save(c));
    }

    @Override
    @Transactional
    public void eliminar(Long cuentaId) {
        Cuenta c = repo.findById(cuentaId)
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));
        repo.delete(c);
    }

    private CuentaResponse toResponse(Cuenta c) {
        return new CuentaResponse(
                c.getCuentaId(),
                c.getNumeroCuenta(),
                c.getTipoCuenta(),
                c.getSaldoDisponible(),
                c.getEstado(),
                c.getClienteId());
    }
}