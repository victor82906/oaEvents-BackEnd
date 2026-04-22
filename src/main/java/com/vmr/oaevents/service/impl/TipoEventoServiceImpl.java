package com.vmr.oaevents.service.impl;

import com.vmr.oaevents.model.TipoEvento;
import com.vmr.oaevents.repository.TipoEventoRepository;
import com.vmr.oaevents.service.TipoEventoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoEventoServiceImpl implements TipoEventoService {

    private final TipoEventoRepository repository;

    @Override
    public List<TipoEvento> findAll() {
        return repository.findAll();
    }

    @Override
    public TipoEvento findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TipoEvento no encontrado con id: " + id));
    }

    @Override
    public TipoEvento save(TipoEvento entity) {
        return repository.save(entity);
    }

    @Override
    public TipoEvento update(Long id, TipoEvento entity) {
        this.findById(id);
        entity.setId(id);
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(this.findById(id));
    }
}
