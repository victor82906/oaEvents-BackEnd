package com.vmr.oaevents.controller;

import com.vmr.oaevents.model.TipoEvento;
import com.vmr.oaevents.model.dto.tipoEvento.TipoEventoInputDto;
import com.vmr.oaevents.model.dto.tipoEvento.TipoEventoOutputDto;
import com.vmr.oaevents.model.mapper.TipoEventoMapper;
import com.vmr.oaevents.service.TipoEventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tipo-evento")
@RequiredArgsConstructor
public class TipoEventoController {

    private final TipoEventoService service;
    private final TipoEventoMapper mapper;

    @GetMapping
    public ResponseEntity<List<TipoEventoOutputDto>> findAll() {
        return ResponseEntity.ok(
                service.findAll().stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoEventoOutputDto> findById(@PathVariable Long id) {
        TipoEvento entity = service.findById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @PostMapping
    public ResponseEntity<TipoEventoOutputDto> create(@Valid @RequestBody TipoEventoInputDto inputDto) {
        TipoEvento entity = mapper.toEntity(inputDto);
        entity = service.save(entity);
        return new ResponseEntity<>(mapper.toDto(entity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoEventoOutputDto> update(@PathVariable Long id, @Valid @RequestBody TipoEventoInputDto inputDto) {
        TipoEvento entity = mapper.toEntity(inputDto);
        entity = service.update(id, entity);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
