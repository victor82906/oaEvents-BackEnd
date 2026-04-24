package com.vmr.oaevents.service;

import com.vmr.oaevents.model.Zona;
import java.util.List;

public interface ZonaService {
    List<Zona> findAll();
    List<Zona> findByRecintoId(Long recintoId);
    Zona findById(Long id);
    Zona save(Zona entity);
    Zona update(Long id, Zona entity);
    void deleteById(Long id);
}
