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

    @GetMapping("/page")
    public ResponseEntity<Page<EntradaOutputDto>> findAllPaged(Pageable pageable) {
        Page<Entrada> page = service.findAll(pageable);
        return ResponseEntity.ok(page.map(mapper::toDto));
    }

    @GetMapping("/comprador/{compradorId}/page")
    public ResponseEntity<Page<EntradaOutputDto>> findByCompradorId(@PathVariable Long compradorId, Pageable pageable) {
        Page<Entrada> page = service.findByCompradorId(compradorId, pageable);
        return ResponseEntity.ok(page.map(mapper::toDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntradaOutputDto> findById(@PathVariable Long id) {
        Entrada entity = service.findById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @PostMapping("/comprar/logueado")
    public ResponseEntity<List<Long>> comprarEntradasLogueado(@Valid @RequestBody EntradaCompraLogueadoInputDto entradaCompraLogueadoInputDto) {
        return ResponseEntity.ok(service.procesarPagoLogueado(entradaCompraLogueadoInputDto));
    }

    @PostMapping("/comprar")
    public ResponseEntity<List<Long>> comprarEntradas(@Valid @RequestBody EntradaCompraInputDto entradaCompraInputDto) {
        return ResponseEntity.ok(service.procesarPago(entradaCompraInputDto));
    }

    @GetMapping("/{id}/descargar")
    public ResponseEntity<byte[]> descargarEntrada(@PathVariable Long id) {
        byte[] pdfBytes = service.descargarEntradaPdf(id);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "entrada_" + id + ".pdf");
        
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EntradaOutputDto> create(@Valid @RequestBody EntradaInputDto inputDto) {
        Entrada entity = mapper.toEntity(inputDto);
        entity = service.save(entity, false);
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
