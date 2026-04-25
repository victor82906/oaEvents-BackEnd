package com.vmr.oaevents.controller;

import com.vmr.oaevents.model.Localidad;
import com.vmr.oaevents.model.dto.localidad.LocalidadInputDto;
import com.vmr.oaevents.model.dto.localidad.LocalidadOutputDto;
import com.vmr.oaevents.model.mapper.LocalidadMapper;
import com.vmr.oaevents.service.LocalidadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/localidad")
@RequiredArgsConstructor
public class LocalidadController {

    private final LocalidadService service;
    private final LocalidadMapper mapper;

    @GetMapping
    public ResponseEntity<List<LocalidadOutputDto>> findAll() {
        return ResponseEntity.ok(
                service.findAll().stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/zona/{zonaId}")
    public ResponseEntity<List<LocalidadOutputDto>> findByZonaId(@PathVariable Long zonaId) {
        return ResponseEntity.ok(
                service.findByZonaId(zonaId).stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{zonaEventoId}/libres")
    public ResponseEntity<List<LocalidadOutputDto>> findLocalidadesLibres(@PathVariable Long zonaEventoId) {
        return ResponseEntity.ok(
                service.findLocalidadesLibresByZonaEventoId(zonaEventoId).stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalidadOutputDto> findById(@PathVariable Long id) {
        Localidad entity = service.findById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @PostMapping
    public ResponseEntity<LocalidadOutputDto> create(@Valid @RequestBody LocalidadInputDto inputDto) {
        Localidad entity = mapper.toEntity(inputDto);
        entity = service.save(entity);
        return new ResponseEntity<>(mapper.toDto(entity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocalidadOutputDto> update(@PathVariable Long id, @Valid @RequestBody LocalidadInputDto inputDto) {
        Localidad entity = mapper.toEntity(inputDto);
        entity = service.update(id, entity);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
