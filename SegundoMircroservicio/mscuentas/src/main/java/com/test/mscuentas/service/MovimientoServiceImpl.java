package com.test.mscuentas.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.mscuentas.dto.MovimientoRequest;
import com.test.mscuentas.dto.MovimientoResponse;
import com.test.mscuentas.exception.NotFoundException;
import com.test.mscuentas.exception.SaldoNoDisponibleException;
import com.test.mscuentas.model.Cuenta;
import com.test.mscuentas.model.Movimiento;
import com.test.mscuentas.repository.CuentaRepository;
import com.test.mscuentas.repository.MovimientoRepository;

@Service
public class MovimientoServiceImpl implements MovimientoService {
    private final CuentaRepository cuentaRepo;
    private final MovimientoRepository movimientoRepo;

    public MovimientoServiceImpl(CuentaRepository cuentaRepo, MovimientoRepository movimientoRepo) {
        this.cuentaRepo = cuentaRepo;
        this.movimientoRepo = movimientoRepo;
    }

    @Override
    @Transactional
    public MovimientoResponse registrar(MovimientoRequest r) {

        Cuenta cuenta = cuentaRepo.findById(r.cuentaId())
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));

        BigDecimal saldoActual = cuenta.getSaldoDisponible();
        BigDecimal nuevoSaldo = saldoActual.add(r.valor());

        // F3: si queda negativo da un error
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoNoDisponibleException();
        }

        // actualizar saldo disponible
        cuenta.setSaldoDisponible(nuevoSaldo);
        cuentaRepo.save(cuenta);

        // registrar movimiento
        Movimiento m = new Movimiento();
        m.setFecha(r.fecha().atStartOfDay());
        m.setTipoMovimiento(r.tipoMovimiento());
        m.setValor(r.valor());
        m.setSaldo(nuevoSaldo);
        m.setCuenta(cuenta);

        Movimiento saved = movimientoRepo.save(m);

        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoResponse> listar() {
        return movimientoRepo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoResponse> listarPorCuenta(Long cuentaId) {
        return movimientoRepo.findByCuenta_CuentaId(cuentaId).stream().map(this::toResponse).toList();
    }

    private MovimientoResponse toResponse(Movimiento m) {
        return new MovimientoResponse(
                m.getMovimientoId(),
                m.getFecha(),
                m.getTipoMovimiento(),
                m.getValor(),
                m.getSaldo(),
                m.getCuenta().getCuentaId());
    }
}
