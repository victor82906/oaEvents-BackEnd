package com.vmr.oaevents.service.impl;

import com.vmr.oaevents.model.Validador;
import com.vmr.oaevents.repository.ValidadorRepository;
import com.vmr.oaevents.service.ValidadorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidadorServiceImpl implements ValidadorService {

    private final ValidadorRepository repository;

    @Override
    public List<Validador> findAll() {
        return repository.findAll();
    }

    @Override
    public Validador findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Validador no encontrado con id: " + id));
    }

    @Override
    public Validador save(Validador entity) {
        return repository.save(entity);
    }

    @Override
    public Validador update(Long id, Validador entity) {
        this.findById(id);
        entity.setId(id);
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(this.findById(id));
    }
}
