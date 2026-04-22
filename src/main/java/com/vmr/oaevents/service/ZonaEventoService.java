package com.vmr.oaevents.service;

import com.vmr.oaevents.model.ZonaEvento;
import java.util.List;

public interface ZonaEventoService {
    List<ZonaEvento> findAll();
    ZonaEvento findById(Long id);
    ZonaEvento save(ZonaEvento entity);
    ZonaEvento update(Long id, ZonaEvento entity);
    void deleteById(Long id);
}
