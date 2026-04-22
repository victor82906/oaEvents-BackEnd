package com.vmr.oaevents.service;

import com.vmr.oaevents.model.Empresa;
import java.util.List;

public interface EmpresaService {
    List<Empresa> findAll();
    Empresa findById(Long id);
    Empresa save(Empresa entity);
    Empresa update(Long id, Empresa entity);
    void deleteById(Long id);
}
