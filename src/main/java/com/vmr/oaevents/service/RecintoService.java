package com.vmr.oaevents.service;

import com.vmr.oaevents.model.Recinto;
import java.util.List;

public interface RecintoService {
    List<Recinto> findAll();
    Recinto findById(Long id);
    Recinto save(Recinto entity);
    Recinto update(Long id, Recinto entity);
    void deleteById(Long id);
}
