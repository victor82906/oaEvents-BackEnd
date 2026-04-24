package com.vmr.oaevents.service;

import com.vmr.oaevents.model.Validador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ValidadorService {
    List<Validador> findAll();
    Page<Validador> findAll(Pageable pageable);
    Validador findById(Long id);
    Validador save(Validador entity);
    Validador update(Long id, Validador entity);
    void deleteById(Long id);
    boolean existByDni(String dni);
    boolean existByDni(String dni, Long id);
}
