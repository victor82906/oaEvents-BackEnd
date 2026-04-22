package com.vmr.oaevents.service;

import com.vmr.oaevents.model.Evento;
import java.util.List;

public interface EventoService {
    List<Evento> findAll();
    Evento findById(Long id);
    Evento save(Evento entity);
    Evento update(Long id, Evento entity);
    void deleteById(Long id);
}
