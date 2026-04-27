package com.vmr.oaevents.service;

import com.vmr.oaevents.model.Entrada;
import com.vmr.oaevents.model.dto.entrada.EntradaCompraInputDto;
import com.vmr.oaevents.model.dto.entrada.EntradaCompraLogueadoInputDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EntradaService {
    List<Entrada> findAll();
    Page<Entrada> findAll(Pageable pageable);
    Page<Entrada> findByCompradorId(Long compradorId, Pageable pageable);
    Entrada findById(Long id);
    Entrada save(Entrada entity, boolean logueado);
    Entrada update(Long id, Entrada entity);
    void deleteById(Long id);
    byte[] descargarEntradaPdf(Long id);
    List<Long> procesarPagoLogueado(EntradaCompraLogueadoInputDto entradaCompraLogueadoInputDto);
    List<Long> procesarPago(EntradaCompraInputDto entradaCompraInputDto);
    boolean isComprador(Long entradaId, Long usuarioId);
}
