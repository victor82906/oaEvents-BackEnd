package com.vmr.oaevents.service;

import com.vmr.oaevents.model.TipoEvento;
import java.util.List;

public interface TipoEventoService {
    List<TipoEvento> findAll();
    TipoEvento findById(Long id);
    TipoEvento save(TipoEvento entity);
    TipoEvento update(Long id, TipoEvento entity);
    void deleteById(Long id);
}
