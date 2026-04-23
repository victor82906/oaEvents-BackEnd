package com.vmr.oaevents.service.impl;

import com.vmr.oaevents.model.Chat;
import com.vmr.oaevents.model.Usuario;
import com.vmr.oaevents.repository.ChatRepository;
import com.vmr.oaevents.service.ChatService;
import com.vmr.oaevents.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository repository;
    private final UsuarioService usuarioService;

    @Override
    public List<Chat> findAll() {
        return repository.findAll();
    }

    @Override
    public Chat findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chat no encontrado con id: " + id));
    }

    @Override
    public Chat save(Chat entity) {
        Usuario emisor = usuarioService.findById(entity.getEmisor().getId());
        Usuario receptor = usuarioService.findById(entity.getReceptor().getId());
        entity.setEmisor(emisor);
        entity.setReceptor(receptor);
        return repository.save(entity);
    }

    @Override
    public Chat update(Long id, Chat entity) {
        Chat chat = this.findById(id);
        entity.setId(id);
        entity.setEmisor(chat.getEmisor());
        entity.setReceptor(chat.getReceptor());
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id);
        repository.delete(this.findById(id));
    }
}
