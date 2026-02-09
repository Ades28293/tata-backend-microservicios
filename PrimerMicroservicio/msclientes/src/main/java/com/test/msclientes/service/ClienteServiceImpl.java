package com.test.msclientes.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.msclientes.dto.ClienteRequest;
import com.test.msclientes.dto.ClienteResponse;
import com.test.msclientes.exception.BadRequestException;
import com.test.msclientes.exception.NotFoundException;
import com.test.msclientes.messaging.ClienteEventPublisher;
import com.test.msclientes.messaging.ClienteUpsertedEvent;
import com.test.msclientes.model.Cliente;
import com.test.msclientes.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repo;
    private final ClienteEventPublisher publisher;

    public ClienteServiceImpl(ClienteRepository repo,
            ClienteEventPublisher publisher) {
        this.repo = repo;
        this.publisher = publisher;
    }

    @Override
    @Transactional
    public ClienteResponse crear(ClienteRequest r) {

        if (repo.existsByIdentificacion(r.identificacion())) {
            throw new BadRequestException("Ya existe un cliente con identificacion=" + r.identificacion());
        }

        Cliente c = new Cliente();
        // Persona
        c.setNombre(r.nombre());
        c.setGenero(r.genero());
        c.setEdad(r.edad());
        c.setIdentificacion(r.identificacion());
        c.setDireccion(r.direccion());
        c.setTelefono(r.telefono());

        
        Long clienteId = r.clienteId();
        if (clienteId == null) {
            Long next = repo.findTopByOrderByClienteIdDesc()
                    .map(x -> x.getClienteId() == null ? 1L : x.getClienteId() + 1L)
                    .orElse(1L);
            clienteId = next;
        } else {
            
            if (repo.existsByClienteId(clienteId)) {
                throw new BadRequestException("Ya existe un cliente con clienteId=" + clienteId);
            }
        }

        c.setClienteId(clienteId);
        c.setContrasena(r.contrasena());
        c.setEstado(r.estado());

        Cliente saved = repo.save(c);

        publisher.publishClienteUpserted(new ClienteUpsertedEvent(
                saved.getClienteId(),
                saved.getIdentificacion(),
                saved.getNombre(),
                saved.getEstado()));

        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponse> listar() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponse obtenerPorClienteId(Long clienteId) {
        Cliente c = repo.findByClienteId(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado: clienteId=" + clienteId));
        return toResponse(c);
    }

    @Override
    @Transactional
    public ClienteResponse actualizar(Long clienteId, ClienteRequest r) {

        Cliente c = repo.findByClienteId(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado: clienteId=" + clienteId));

        if (!c.getIdentificacion().equals(r.identificacion())
                && repo.existsByIdentificacion(r.identificacion())) {
            throw new BadRequestException("Ya existe un cliente con identificacion=" + r.identificacion());
        }

        c.setNombre(r.nombre());
        c.setGenero(r.genero());
        c.setEdad(r.edad());
        c.setIdentificacion(r.identificacion());
        c.setDireccion(r.direccion());
        c.setTelefono(r.telefono());
        c.setContrasena(r.contrasena());
        c.setEstado(r.estado());

        Cliente saved = repo.save(c);

        // evento que actualzia 
        publisher.publishClienteUpserted(new ClienteUpsertedEvent(
                saved.getClienteId(),
                saved.getIdentificacion(),
                saved.getNombre(),
                saved.getEstado()));

        return toResponse(saved);
    }

    @Override
    @Transactional
    public void eliminar(Long clienteId) {
        Cliente c = repo.findByClienteId(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado: clienteId=" + clienteId));
        repo.delete(c);
    }

    private ClienteResponse toResponse(Cliente c) {
        return new ClienteResponse(
                c.getClienteId(),
                c.getNombre(),
                c.getGenero(),
                c.getEdad(),
                c.getIdentificacion(),
                c.getDireccion(),
                c.getTelefono(),
                c.getEstado());
    }
}
