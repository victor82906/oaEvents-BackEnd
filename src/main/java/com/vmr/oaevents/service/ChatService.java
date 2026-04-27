package com.vmr.oaevents.service;

import com.vmr.oaevents.model.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatService {
    List<Chat> findAll();
    Page<Chat> findConversation(Long emisorId, Long receptorId, Pageable pageable);
    Chat findById(Long id);
    Chat save(Chat chat);
    Chat update(Long id, Chat chat);
    void deleteById(Long id);
    boolean isEmisor(Long chatId, Long usuarioId);
}
