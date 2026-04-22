package com.vmr.oaevents.service.impl;

import com.vmr.oaevents.model.Recinto;
import com.vmr.oaevents.repository.RecintoRepository;
import com.vmr.oaevents.service.RecintoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecintoServiceImpl implements RecintoService {

    private final RecintoRepository repository;

    @Override
    public List<Recinto> findAll() {
        return repository.findAll();
    }

    @Override
    public Recinto findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recinto no encontrado con id: " + id));
    }

    @Override
    public Recinto save(Recinto entity) {
        return repository.save(entity);
    }

    @Override
    public Recinto update(Long id, Recinto entity) {
        this.findById(id);
        entity.setId(id);
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(this.findById(id));
    }
}
