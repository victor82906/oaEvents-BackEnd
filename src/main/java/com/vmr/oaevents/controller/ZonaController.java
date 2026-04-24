package com.vmr.oaevents.controller;

import com.vmr.oaevents.model.Zona;
import com.vmr.oaevents.model.dto.zona.ZonaInputDto;
import com.vmr.oaevents.model.dto.zona.ZonaOutputDto;
import com.vmr.oaevents.model.mapper.ZonaMapper;
import com.vmr.oaevents.service.ZonaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/zona")
@RequiredArgsConstructor
public class ZonaController {

    private final ZonaService service;
    private final ZonaMapper mapper;

    @GetMapping
    public ResponseEntity<List<ZonaOutputDto>> findAll() {
        return ResponseEntity.ok(
                service.findAll().stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/recinto/{recintoId}")
    public ResponseEntity<List<ZonaOutputDto>> findByRecintoId(@PathVariable Long recintoId) {
        return ResponseEntity.ok(
                service.findByRecintoId(recintoId).stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZonaOutputDto> findById(@PathVariable Long id) {
        Zona entity = service.findById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @PostMapping
    public ResponseEntity<ZonaOutputDto> create(@Valid @RequestBody ZonaInputDto inputDto) {
        Zona entity = mapper.toEntity(inputDto);
        entity = service.save(entity);
        return new ResponseEntity<>(mapper.toDto(entity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZonaOutputDto> update(@PathVariable Long id, @Valid @RequestBody ZonaInputDto inputDto) {
        Zona entity = mapper.toEntity(inputDto);
        entity = service.update(id, entity);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
