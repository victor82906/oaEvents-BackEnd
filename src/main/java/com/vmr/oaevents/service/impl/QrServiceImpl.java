package com.vmr.oaevents.service.impl;

import com.vmr.oaevents.model.Qr;
import com.vmr.oaevents.repository.QrRepository;
import com.vmr.oaevents.service.QrService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QrServiceImpl implements QrService {

    private final QrRepository repository;

    @Override
    public List<Qr> findAll() {
        return repository.findAll();
    }

    @Override
    public Qr findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Qr no encontrado con id: " + id));
    }

    @Override
    public Qr save(Qr entity) {
        return repository.save(entity);
    }

    @Override
    public Qr update(Long id, Qr entity) {
        this.findById(id);
        entity.setId(id);
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(this.findById(id));
    }
}
