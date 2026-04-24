package com.vmr.oaevents.service.impl;

import com.vmr.oaevents.model.*;
import com.vmr.oaevents.repository.EntradaRepository;
import com.vmr.oaevents.service.CompradorService;
import com.vmr.oaevents.service.EntradaService;
import com.vmr.oaevents.service.LocalidadService;
import com.vmr.oaevents.service.ZonaEventoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EntradaServiceImpl implements EntradaService {

    private final EntradaRepository repository;
    private final LocalidadService localidadService;
    private final ZonaEventoService zonaEventoService;
    private final CompradorService compradorService;
    private final QrGeneratorService qrGeneratorService;

    @Override
    public List<Entrada> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Entrada> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Entrada> findByCompradorId(Long compradorId, Pageable pageable) {
        // Valida que el comprador exista (opcional pero recomendable)
        compradorService.findById(compradorId);
        return repository.findByCompradorId(compradorId, pageable);
    }

    @Override
    public Entrada findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entrada no encontrada con id: " + id));
    }

    @Override
    public Entrada save(Entrada entity) {
        Localidad localidad = localidadService.findById(entity.getLocalidad().getId());
        ZonaEvento zonaEvento = zonaEventoService.findById(entity.getZonaEvento().getId());
        Comprador comprador = compradorService.findById(entity.getComprador().getId());

        String codigo = UUID.randomUUID().toString();
        String rutaQr = qrGeneratorService.generateQr(codigo);

        Qr qr = new Qr();
        qr.setCodigo(codigo);
        qr.setFoto(rutaQr);

        entity.setQr(qr);
        entity.setLocalidad(localidad);
        entity.setZonaEvento(zonaEvento);
        entity.setComprador(comprador);
        entity.setFechaCompra(LocalDateTime.now());
        entity.setNombreComprador(comprador.getNombre());
        entity.setDniComprador(comprador.getDni());
        entity.setPrecio(zonaEvento.getPrecio());
        return repository.save(entity);
    }

    @Override
    public Entrada update(Long id, Entrada entity) {
        Entrada entrada = this.findById(id);
        entity.setId(id);
        entity.setLocalidad(entrada.getLocalidad());
        entity.setZonaEvento(entrada.getZonaEvento());
        Comprador comprador = compradorService.findById(entity.getComprador().getId());
        entity.setComprador(comprador);
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id);
        repository.delete(this.findById(id));
    }
}
