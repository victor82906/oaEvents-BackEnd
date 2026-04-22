package com.vmr.oaevents.controller;

import com.vmr.oaevents.model.Chat;
import com.vmr.oaevents.model.dto.chat.ChatInputDto;
import com.vmr.oaevents.model.dto.chat.ChatOutputDto;
import com.vmr.oaevents.model.mapper.ChatMapper;
import com.vmr.oaevents.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService service;
    private final ChatMapper mapper;

    @GetMapping
    public ResponseEntity<List<ChatOutputDto>> findAll() {
        return ResponseEntity.ok(
                service.findAll().stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatOutputDto> findById(@PathVariable Long id) {
        Chat entity = service.findById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @PostMapping
    public ResponseEntity<ChatOutputDto> create(@Valid @RequestBody ChatInputDto inputDto) {
        Chat entity = mapper.toEntity(inputDto);
        entity = service.save(entity);
        return new ResponseEntity<>(mapper.toDto(entity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChatOutputDto> update(@PathVariable Long id, @Valid @RequestBody ChatInputDto inputDto) {
        Chat entity = mapper.toEntity(inputDto);
        entity = service.update(id, entity);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
