package com.vmr.oaevents.service;

import com.vmr.oaevents.model.Comprador;
import java.util.List;

public interface CompradorService {
    List<Comprador> findAll();
    Comprador findById(Long id);
    Comprador save(Comprador entity);
    Comprador update(Long id, Comprador entity);
    void deleteById(Long id);
    boolean existByDni(String dni);
}
