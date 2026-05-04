package com.vmr.oaevents.controller;

import com.vmr.oaevents.model.Entrada;
import com.vmr.oaevents.model.dto.entrada.EntradaCompraInputDto;
import com.vmr.oaevents.model.dto.entrada.EntradaCompraLogueadoInputDto;
import com.vmr.oaevents.model.dto.entrada.EntradaInputDto;
import com.vmr.oaevents.model.dto.entrada.EntradaOutputDto;
import com.vmr.oaevents.model.mapper.EntradaMapper;
import com.vmr.oaevents.service.EntradaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('RECINTO')")
    public ResponseEntity<List<EntradaOutputDto>> findAll() {
        return ResponseEntity.ok(
                service.findAll().stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/page")
    @PreAuthorize("hasRole('RECINTO')")
    public ResponseEntity<Page<EntradaOutputDto>> findAllPaged(Pageable pageable) {
        Page<Entrada> page = service.findAll(pageable);
        return ResponseEntity.ok(page.map(mapper::toDto));
    }

    @GetMapping("/comprador/{compradorId}/page")
    @PreAuthorize("hasRole('RECINTO') or principal.id == #compradorId")
    public ResponseEntity<Page<EntradaOutputDto>> findByCompradorId(@PathVariable Long compradorId, Pageable pageable) {
        Page<Entrada> page = service.findByCompradorId(compradorId, pageable);
        return ResponseEntity.ok(page.map(mapper::toDto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('RECINTO') or @entradaServiceImpl.isComprador(#id, principal.id)")
    public ResponseEntity<EntradaOutputDto> findById(@PathVariable Long id) {
        Entrada entity = service.findById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @PostMapping("/comprar/logueado")
    @PreAuthorize("hasRole('COMPRADOR')")
    public ResponseEntity<List<Long>> comprarEntradasLogueado(@Valid @RequestBody EntradaCompraLogueadoInputDto entradaCompraLogueadoInputDto) {
        return ResponseEntity.ok(service.procesarPagoLogueado(entradaCompraLogueadoInputDto));
    }

    @PostMapping("/comprar")
    public ResponseEntity<List<Long>> comprarEntradas(@Valid @RequestBody EntradaCompraInputDto entradaCompraInputDto) {
        return ResponseEntity.ok(service.procesarPago(entradaCompraInputDto));
    }

    @GetMapping("/{id}/descargar")
    @PreAuthorize("@entradaServiceImpl.isComprador(#id, principal.id)")
    public ResponseEntity<byte[]> descargarEntrada(@PathVariable Long id) {
        byte[] pdfBytes = service.descargarEntradaPdf(id);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "entrada_" + id + ".pdf");
        
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('RECINTO')")
    public ResponseEntity<EntradaOutputDto> create(@Valid @RequestBody EntradaInputDto inputDto) {
        Entrada entity = mapper.toEntity(inputDto);
        entity = service.save(entity, false);
        return new ResponseEntity<>(mapper.toDto(entity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('RECINTO')")
    public ResponseEntity<EntradaOutputDto> update(@PathVariable Long id, @Valid @RequestBody EntradaInputDto inputDto) {
        Entrada entity = mapper.toEntity(inputDto);
        entity = service.update(id, entity);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('RECINTO') or @entradaServiceImpl.isComprador(#id, principal.id)")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
