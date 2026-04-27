package com.vmr.oaevents.service;

import com.vmr.oaevents.model.ZonaEvento;
import java.util.List;

public interface ZonaEventoService {
    List<ZonaEvento> findAll();
    List<ZonaEvento> findByEventoId(Long eventoId);
    ZonaEvento findById(Long id);
    ZonaEvento save(ZonaEvento entity);
    ZonaEvento update(Long id, ZonaEvento entity);
    void deleteById(Long id);
    boolean isPropietario(Long zonaEventoId, Long empresaId);
}
