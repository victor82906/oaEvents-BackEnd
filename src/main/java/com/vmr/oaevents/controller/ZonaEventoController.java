package com.vmr.oaevents.controller;

import com.vmr.oaevents.model.ZonaEvento;
import com.vmr.oaevents.model.dto.zonaEvento.ZonaEventoInputDto;
import com.vmr.oaevents.model.dto.zonaEvento.ZonaEventoOutputDto;
import com.vmr.oaevents.model.mapper.ZonaEventoMapper;
import com.vmr.oaevents.service.ZonaEventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/zona-evento")
@RequiredArgsConstructor
public class ZonaEventoController {

    private final ZonaEventoService service;
    private final ZonaEventoMapper mapper;

    @GetMapping
    public ResponseEntity<List<ZonaEventoOutputDto>> findAll() {
        return ResponseEntity.ok(
                service.findAll().stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<ZonaEventoOutputDto>> findByEventoId(@PathVariable Long eventoId) {
        return ResponseEntity.ok(
                service.findByEventoId(eventoId).stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZonaEventoOutputDto> findById(@PathVariable Long id) {
        ZonaEvento entity = service.findById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @PostMapping
    public ResponseEntity<ZonaEventoOutputDto> create(@Valid @RequestBody ZonaEventoInputDto inputDto) {
        ZonaEvento entity = mapper.toEntity(inputDto);
        entity = service.save(entity);
        return new ResponseEntity<>(mapper.toDto(entity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZonaEventoOutputDto> update(@PathVariable Long id, @Valid @RequestBody ZonaEventoInputDto inputDto) {
        ZonaEvento entity = mapper.toEntity(inputDto);
        entity = service.update(id, entity);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
