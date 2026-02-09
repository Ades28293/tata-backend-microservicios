package com.test.mscuentas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.mscuentas.model.ClienteRef;

public interface ClienteRefRepository extends JpaRepository<ClienteRef, Long>{
    
}
