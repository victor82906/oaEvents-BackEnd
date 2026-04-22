package com.vmr.oaevents.service.impl;

import com.vmr.oaevents.model.Comprador;
import com.vmr.oaevents.repository.CompradorRepository;
import com.vmr.oaevents.service.CompradorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompradorServiceImpl implements CompradorService {

    private final CompradorRepository repository;

    @Override
    public List<Comprador> findAll() {
        return repository.findAll();
    }

    @Override
    public Comprador findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comprador no encontrado con id: " + id));
    }

    @Override
    public Comprador save(Comprador entity) {
        return repository.save(entity);
    }

    @Override
    public Comprador update(Long id, Comprador entity) {
        this.findById(id);
        entity.setId(id);
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(this.findById(id));
    }
}
