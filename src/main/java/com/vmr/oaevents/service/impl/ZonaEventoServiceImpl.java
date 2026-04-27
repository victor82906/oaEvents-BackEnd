package com.vmr.oaevents.service.impl;

import com.vmr.oaevents.model.Evento;
import com.vmr.oaevents.model.Zona;
import com.vmr.oaevents.model.ZonaEvento;
import com.vmr.oaevents.repository.ZonaEventoRepository;
import com.vmr.oaevents.service.EventoService;
import com.vmr.oaevents.service.ZonaEventoService;
import com.vmr.oaevents.service.ZonaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZonaEventoServiceImpl implements ZonaEventoService {

    private final ZonaEventoRepository repository;
    private final EventoService eventoService;
    private final ZonaService zonaService;

    @Override
    public List<ZonaEvento> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ZonaEvento> findByEventoId(Long eventoId) {
        eventoService.findById(eventoId); // Validar que el evento existe
        return repository.findByEventoId(eventoId);
    }

    @Override
    public ZonaEvento findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ZonaEvento no encontrado con id: " + id));
    }

    @Override
    public ZonaEvento save(ZonaEvento entity) {
        Evento evento = eventoService.findById(entity.getEvento().getId());
        Zona zona = zonaService.findById(entity.getZona().getId());
        entity.setEvento(evento);
        entity.setZona(zona);
        return repository.save(entity);
    }

    @Override
    public ZonaEvento update(Long id, ZonaEvento entity) {
        ZonaEvento zonaEvento = this.findById(id);
        entity.setId(id);
        entity.setEvento(zonaEvento.getEvento());
        entity.setZona(zonaEvento.getZona());
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        ZonaEvento zonaEvento = this.findById(id);
        zonaEvento.getEntradas()
                .forEach(entrada -> entrada.setZonaEvento(null));
        repository.delete(zonaEvento);
    }

    @Override
    public boolean isPropietario(Long zonaEventoId, Long empresaId) {
        ZonaEvento zonaEvento = this.findById(zonaEventoId);
        return zonaEvento.getEvento() != null 
               && zonaEvento.getEvento().getEmpresa() != null 
               && zonaEvento.getEvento().getEmpresa().getId().equals(empresaId);
    }
}
