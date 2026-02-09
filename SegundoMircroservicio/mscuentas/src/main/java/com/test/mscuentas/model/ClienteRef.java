package com.test.mscuentas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "cliente_ref")
public class ClienteRef {

    @Id
    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(name = "identificacion", length = 20)
    private String identificacion;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "estado")
    private Boolean estado;
}
