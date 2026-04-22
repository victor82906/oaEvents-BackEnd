package com.vmr.oaevents.service;

import com.vmr.oaevents.model.Rol;
import java.util.List;

public interface RolService {
    List<Rol> findAll();
    Rol findById(Long id);
    Rol findByNombre(String nombre);
    Rol save(Rol entity);
    Rol update(Long id, Rol entity);
    void deleteById(Long id);
}
