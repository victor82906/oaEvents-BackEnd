package com.vmr.oaevents.service;

import com.vmr.oaevents.model.Validador;
import java.util.List;

public interface ValidadorService {
    List<Validador> findAll();
    Validador findById(Long id);
    Validador save(Validador entity);
    Validador update(Long id, Validador entity);
    void deleteById(Long id);
    boolean existByDni(String dni);
    boolean existByDni(String dni, Long id);
}
