package com.vmr.oaevents.controller;

import com.vmr.oaevents.model.Usuario;
import com.vmr.oaevents.model.dto.usuario.CambiarContrasenaDto;
import com.vmr.oaevents.model.dto.usuario.UsuarioInputDto;
import com.vmr.oaevents.model.dto.usuario.UsuarioOutputDto;
import com.vmr.oaevents.model.mapper.UsuarioMapper;
import com.vmr.oaevents.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @GetMapping
    public ResponseEntity<List<UsuarioOutputDto>> findAll() {
        return ResponseEntity.ok(
                service.findAll().stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioOutputDto> findById(@PathVariable Long id) {
        Usuario entity = service.findById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @PostMapping
    public ResponseEntity<UsuarioOutputDto> create(@Valid @RequestBody UsuarioInputDto inputDto) {
        Usuario entity = mapper.toEntity(inputDto);
        entity = service.save(entity);
        return new ResponseEntity<>(mapper.toDto(entity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioOutputDto> update(@PathVariable Long id, @Valid @RequestBody UsuarioInputDto inputDto) {
        Usuario entity = mapper.toEntity(inputDto);
        entity = service.update(id, entity);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/contrasena")
    @PreAuthorize("principal.id == #id")
    public ResponseEntity<Map<String, String>> cambiarContrasena(@PathVariable Long id, @Valid @RequestBody CambiarContrasenaDto cambiarContrasenaDto){
        service.cambiarContrasena(id, cambiarContrasenaDto);
        return ResponseEntity.ok(Map.of("mensaje", "Contraseña cambiada correctamente"));
    }

}
