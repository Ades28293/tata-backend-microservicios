package com.test.mscuentas.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.mscuentas.model.Movimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    public List<Movimiento> findByCuenta_CuentaId(Long cuentaId);

    public List<Movimiento> findByCuenta_ClienteIdAndFechaBetween(Long clienteId, LocalDateTime desde,
            LocalDateTime hasta);

    public List<Movimiento> findByCuenta_CuentaIdAndFechaBetween(Long cuentaId, LocalDateTime desde,
            LocalDateTime hasta);
}