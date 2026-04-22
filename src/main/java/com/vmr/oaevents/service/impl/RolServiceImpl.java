package com.vmr.oaevents.service.impl;

import com.vmr.oaevents.model.Rol;
import com.vmr.oaevents.repository.RolRepository;
import com.vmr.oaevents.service.RolService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository repository;

    @Override
    public List<Rol> findAll() {
        return repository.findAll();
    }

    @Override
    public Rol findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con id: " + id));
    }

    @Override
    public Rol save(Rol entity) {
        return repository.save(entity);
    }

    @Override
    public Rol update(Long id, Rol entity) {
        this.findById(id);
        entity.setId(id);
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(this.findById(id));
    }
}
