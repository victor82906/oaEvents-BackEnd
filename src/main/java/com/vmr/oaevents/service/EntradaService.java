package com.vmr.oaevents.service;

import com.vmr.oaevents.model.Entrada;
import java.util.List;

public interface EntradaService {
    List<Entrada> findAll();
    Entrada findById(Long id);
    Entrada save(Entrada entity);
    Entrada update(Long id, Entrada entity);
    void deleteById(Long id);
}
