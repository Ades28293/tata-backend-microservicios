package com.test.mscuentas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "cuenta", uniqueConstraints = {
        @UniqueConstraint(name = "uk_cuenta_numero", columnNames = "numero_cuenta")
})
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuenta_id")
    private Long cuentaId;

    @NotBlank
    @Size(max = 30)
    @Column(name = "numero_cuenta", nullable = false, length = 30, unique = true)
    private String numeroCuenta;

    @NotBlank
    @Size(max = 20)
    @Column(name = "tipo_cuenta", nullable = false, length = 20)
    private String tipoCuenta;

    @NotNull
    @DecimalMin(value = "0.00")
    @Column(name = "saldo_inicial", nullable = false, precision = 19, scale = 2)
    private BigDecimal saldoInicial;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Boolean estado;

    // reportes por cliente 
    @NotNull
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    // Saldo disponible actual que se se actualiza con movimientos
    @NotNull
    @Column(name = "saldo_disponible", nullable = false, precision = 19, scale = 2)
    private BigDecimal saldoDisponible;
}
