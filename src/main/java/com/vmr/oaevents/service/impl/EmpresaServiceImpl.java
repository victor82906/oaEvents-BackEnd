package com.vmr.oaevents.service.impl;

import com.vmr.oaevents.model.Empresa;
import com.vmr.oaevents.repository.EmpresaRepository;
import com.vmr.oaevents.service.EmpresaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository repository;

    @Override
    public List<Empresa> findAll() {
        return repository.findAll();
    }

    @Override
    public Empresa findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empresa no encontrada con id: " + id));
    }

    @Override
    public Empresa save(Empresa entity) {
        return repository.save(entity);
    }

    @Override
    public Empresa update(Long id, Empresa entity) {
        this.findById(id);
        entity.setId(id);
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(this.findById(id));
    }
}
