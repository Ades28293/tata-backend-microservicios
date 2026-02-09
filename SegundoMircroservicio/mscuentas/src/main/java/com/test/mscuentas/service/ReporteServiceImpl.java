package com.test.mscuentas.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.mscuentas.dto.ReporteEstadoCuentaResponse;
import com.test.mscuentas.exception.NotFoundException;
import com.test.mscuentas.model.ClienteRef;
import com.test.mscuentas.model.Cuenta;
import com.test.mscuentas.model.Movimiento;
import com.test.mscuentas.repository.ClienteRefRepository;
import com.test.mscuentas.repository.CuentaRepository;
import com.test.mscuentas.repository.MovimientoRepository;

@Service
public class ReporteServiceImpl  implements ReporteService {

    private final CuentaRepository cuentaRepo;
    private final MovimientoRepository movimientoRepo;
    private final ClienteRefRepository clienteRefRepo;

    public ReporteServiceImpl(CuentaRepository cuentaRepo,
                              MovimientoRepository movimientoRepo,
                              ClienteRefRepository clienteRefRepo) {
        this.cuentaRepo = cuentaRepo;
        this.movimientoRepo = movimientoRepo;
        this.clienteRefRepo = clienteRefRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public ReporteEstadoCuentaResponse generarEstadoCuenta(Long clienteId, LocalDate desde, LocalDate hasta) {

       
        LocalDateTime start = desde.atStartOfDay();
        LocalDateTime end = hasta.atTime(LocalTime.MAX);

        ClienteRef cliente = clienteRefRepo.findById(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado en ms-cuentas (cliente_ref): " + clienteId));

        List<Cuenta> cuentas = cuentaRepo.findByClienteId(clienteId);

        List<ReporteEstadoCuentaResponse.CuentaReporte> cuentasReporte = cuentas.stream().map(c -> {

            List<Movimiento> movs = movimientoRepo
                    .findByCuenta_CuentaIdAndFechaBetween(c.getCuentaId(), start, end);

            List<ReporteEstadoCuentaResponse.MovimientoReporte> movsReporte =
                    movs.stream().map(m -> new ReporteEstadoCuentaResponse.MovimientoReporte(
                            m.getMovimientoId(),
                            m.getFecha(),
                            m.getTipoMovimiento(),
                            m.getValor(),
                            m.getSaldo()
                    )).toList();

            return new ReporteEstadoCuentaResponse.CuentaReporte(
                    c.getCuentaId(),
                    c.getNumeroCuenta(),
                    c.getTipoCuenta(),
                    c.getSaldoDisponible(),
                    movsReporte
            );
        }).toList();

        return new ReporteEstadoCuentaResponse(
                cliente.getClienteId(),
                cliente.getNombre(),
                cliente.getIdentificacion(),
                cuentasReporte
        );
    }
}

