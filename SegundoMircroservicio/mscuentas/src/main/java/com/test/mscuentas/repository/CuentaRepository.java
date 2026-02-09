package com.test.mscuentas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.mscuentas.model.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    public Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);

    public List<Cuenta> findByClienteId(Long clienteId);

    public boolean existsByNumeroCuenta(String numeroCuenta);
}
