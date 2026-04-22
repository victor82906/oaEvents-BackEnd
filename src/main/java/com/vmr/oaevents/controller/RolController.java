package com.vmr.oaevents.controller;

import com.vmr.oaevents.model.Rol;
import com.vmr.oaevents.model.dto.rol.RolInputDto;
import com.vmr.oaevents.model.dto.rol.RolOutputDto;
import com.vmr.oaevents.model.mapper.RolMapper;
import com.vmr.oaevents.service.RolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rol")
@RequiredArgsConstructor
public class RolController {

    private final RolService service;
    private final RolMapper mapper;

    @GetMapping
    public ResponseEntity<List<RolOutputDto>> findAll() {
        return ResponseEntity.ok(
                service.findAll().stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolOutputDto> findById(@PathVariable Long id) {
        Rol entity = service.findById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @PostMapping
    public ResponseEntity<RolOutputDto> create(@Valid @RequestBody RolInputDto inputDto) {
        Rol entity = mapper.toEntity(inputDto);
        entity = service.save(entity);
        return new ResponseEntity<>(mapper.toDto(entity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolOutputDto> update(@PathVariable Long id, @Valid @RequestBody RolInputDto inputDto) {
        Rol entity = mapper.toEntity(inputDto);
        entity = service.update(id, entity);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
