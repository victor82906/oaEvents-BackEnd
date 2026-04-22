package com.vmr.oaevents.controller;

import com.vmr.oaevents.model.Evento;
import com.vmr.oaevents.model.dto.evento.EventoInputDto;
import com.vmr.oaevents.model.dto.evento.EventoOutputDto;
import com.vmr.oaevents.model.mapper.EventoMapper;
import com.vmr.oaevents.service.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/evento")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService service;
    private final EventoMapper mapper;

    @GetMapping
    public ResponseEntity<List<EventoOutputDto>> findAll() {
        return ResponseEntity.ok(
                service.findAll().stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoOutputDto> findById(@PathVariable Long id) {
        Evento entity = service.findById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @PostMapping
    public ResponseEntity<EventoOutputDto> create(@Valid @RequestBody EventoInputDto inputDto) {
        Evento entity = mapper.toEntity(inputDto);
        entity = service.save(entity);
        return new ResponseEntity<>(mapper.toDto(entity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoOutputDto> update(@PathVariable Long id, @Valid @RequestBody EventoInputDto inputDto) {
        Evento entity = mapper.toEntity(inputDto);
        entity = service.update(id, entity);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
