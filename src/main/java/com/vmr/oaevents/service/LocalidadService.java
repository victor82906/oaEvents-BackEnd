package com.vmr.oaevents.service;

import com.vmr.oaevents.model.Localidad;
import java.util.List;

public interface LocalidadService {
    List<Localidad> findAll();
    List<Localidad> findByZonaId(Long zonaId);
    Localidad findById(Long id);
    Localidad save(Localidad entity);
    Localidad update(Long id, Localidad entity);
    void deleteById(Long id);
}
