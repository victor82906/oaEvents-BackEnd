package com.vmr.oaevents.service;

import com.vmr.oaevents.model.Chat;
import java.util.List;

public interface ChatService {
    List<Chat> findAll();
    Chat findById(Long id);
    Chat save(Chat chat);
    Chat update(Long id, Chat chat);
    void deleteById(Long id);
}
