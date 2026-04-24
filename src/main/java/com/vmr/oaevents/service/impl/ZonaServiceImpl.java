package com.vmr.oaevents.service.impl;

import com.vmr.oaevents.model.Recinto;
import com.vmr.oaevents.model.Zona;
import com.vmr.oaevents.repository.ZonaRepository;
import com.vmr.oaevents.service.RecintoService;
import com.vmr.oaevents.service.ZonaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZonaServiceImpl implements ZonaService {

    private final ZonaRepository repository;
    private final RecintoService recintoService;

    @Override
    public List<Zona> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Zona> findByRecintoId(Long recintoId) {
        recintoService.findById(recintoId); // Validar que el recinto existe
        return repository.findByRecintoId(recintoId);
    }

    @Override
    public Zona findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Zona no encontrada con id: " + id));
    }

    @Override
    public Zona save(Zona entity) {
        Recinto recinto = recintoService.findById(entity.getRecinto().getId());
        entity.setRecinto(recinto);
        return repository.save(entity);
    }

    @Override
    public Zona update(Long id, Zona entity) {
        Zona zona = this.findById(id);
        entity.setId(id);
        entity.setRecinto(zona.getRecinto());
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        Zona zona = this.findById(id);
        zona.getZonaEventos()
                .forEach(zonaEvento -> zonaEvento.setZona(null));
        zona.getLocalidades()
                .forEach(localidad -> localidad.setZona(null));
        repository.delete(this.findById(id));
    }
}
