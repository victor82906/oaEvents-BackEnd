package com.vmr.oaevents.service.impl;

import com.vmr.oaevents.model.Localidad;
import com.vmr.oaevents.model.Zona;
import com.vmr.oaevents.repository.LocalidadRepository;
import com.vmr.oaevents.service.LocalidadService;
import com.vmr.oaevents.service.ZonaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalidadServiceImpl implements LocalidadService {

    private final LocalidadRepository repository;
    private final ZonaService zonaService;

    @Override
    public List<Localidad> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Localidad> findByZonaId(Long zonaId) {
        zonaService.findById(zonaId); // Validar existencia de la zona
        return repository.findByZonaId(zonaId);
    }

    @Override
    public Localidad findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Localidad no encontrada con id: " + id));
    }

    @Override
    public Localidad save(Localidad entity) {
        Zona zona = zonaService.findById(entity.getZona().getId());
        entity.setZona(zona);
        return repository.save(entity);
    }

    @Override
    public Localidad update(Long id, Localidad entity) {
        Localidad localidad = this.findById(id);
        entity.setId(id);
        entity.setZona(localidad.getZona());
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        Localidad localidad = this.findById(id);
        // comprobar con y sin esto
        localidad.getEntradas()
                .forEach(entrada -> entrada.setLocalidad(null));
        repository.delete(this.findById(id));
    }
}
