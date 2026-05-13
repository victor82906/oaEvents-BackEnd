package com.vmr.oaevents.service.kafka;

import com.vmr.oaevents.model.dto.chat.ChatOutputDto;
import com.vmr.oaevents.model.mapper.ChatMapper;
import com.vmr.oaevents.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatKafkaConsumer {

    private final ChatService chatService;
    private final ChatMapper mapper;
    private final SimpMessagingTemplate messagingTemplate; // Herramienta de WebSockets

    @KafkaListener(topics = "chat-topic")
    public void procesarMensaje(ChatOutputDto chatOutputDto) {
        System.out.println("FUNCIONA KAFKA");

        // 3. Enviamos el mensaje EN TIEMPO REAL al receptor mediante WebSocket
        messagingTemplate.convertAndSend(
                "/topic/mensajes/" + chatOutputDto.getReceptor_id(),
                chatOutputDto
        );
    }
}