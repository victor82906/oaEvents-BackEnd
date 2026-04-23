package com.vmr.oaevents.service.impl;

import com.vmr.oaevents.model.Empresa;
import com.vmr.oaevents.model.Evento;
import com.vmr.oaevents.model.TipoEvento;
import com.vmr.oaevents.model.ZonaEvento;
import com.vmr.oaevents.repository.EventoRepository;
import com.vmr.oaevents.service.EmpresaService;
import com.vmr.oaevents.service.EventoService;
import com.vmr.oaevents.service.TipoEventoService;
import com.vmr.oaevents.service.ZonaEventoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoServiceImpl implements EventoService {

    private final EventoRepository repository;
    private final TipoEventoService tipoEventoService;
    private final EmpresaService empresaService;
    private final ZonaEventoService zonaEventoService;

    @Override
    public List<Evento> findAll() {
        return repository.findAll();
    }

    @Override
    public Evento findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado con id: " + id));
    }

    @Override
    public Evento save(Evento entity) {
        TipoEvento tipoEvento = tipoEventoService.findById(entity.getTipoEvento().getId());
        Empresa empresa = empresaService.findById(entity.getEmpresa().getId());
        entity.setTipoEvento(tipoEvento);
        entity.setEmpresa(empresa);
        entity.setAceptado(false);
        return repository.save(entity);
    }

    @Override
    public Evento update(Long id, Evento entity) {
        Evento evento = this.findById(id);
        entity.setId(id);
        entity.setEmpresa(evento.getEmpresa());
        entity.setAceptado(evento.isAceptado());
        TipoEvento tipoEvento = tipoEventoService.findById(entity.getTipoEvento().getId());
        entity.setTipoEvento(tipoEvento);
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        Evento evento = this.findById(id);
        evento.getZonasEvento().stream()
                .map(ZonaEvento::getId)
                .forEach(this.zonaEventoService::deleteById);
        repository.delete(this.findById(id));
    }
}
