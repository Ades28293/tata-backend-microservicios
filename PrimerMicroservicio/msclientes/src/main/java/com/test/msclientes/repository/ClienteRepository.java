package com.test.msclientes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.msclientes.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    Optional<Cliente> findByClienteId(Long clienteId);
    boolean existsByClienteId(Long clienteId);
    boolean existsByIdentificacion(String identificacion);
    Optional<Cliente> findTopByOrderByClienteIdDesc();

}
