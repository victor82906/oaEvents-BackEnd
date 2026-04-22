package com.vmr.oaevents.controller;

import com.vmr.oaevents.model.Entrada;
import com.vmr.oaevents.model.dto.entrada.EntradaInputDto;
import com.vmr.oaevents.model.dto.entrada.EntradaOutputDto;
import com.vmr.oaevents.model.mapper.EntradaMapper;
import com.vmr.oaevents.service.EntradaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/entrada")
@RequiredArgsConstructor
public class EntradaController {

    private final EntradaService service;
    private final EntradaMapper mapper;

    @GetMapping
    public ResponseEntity<List<EntradaOutputDto>> findAll() {
        return ResponseEntity.ok(
                service.findAll().stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntradaOutputDto> findById(@PathVariable Long id) {
        Entrada entity = service.findById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @PostMapping
    public ResponseEntity<EntradaOutputDto> create(@Valid @RequestBody EntradaInputDto inputDto) {
        Entrada entity = mapper.toEntity(inputDto);
        entity = service.save(entity);
        return new ResponseEntity<>(mapper.toDto(entity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntradaOutputDto> update(@PathVariable Long id, @Valid @RequestBody EntradaInputDto inputDto) {
        Entrada entity = mapper.toEntity(inputDto);
        entity = service.update(id, entity);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
