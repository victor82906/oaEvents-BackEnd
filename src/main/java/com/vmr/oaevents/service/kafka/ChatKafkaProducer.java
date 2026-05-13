package com.vmr.oaevents.service.kafka;

import com.vmr.oaevents.model.dto.chat.ChatOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatKafkaProducer {

    private final KafkaTemplate<String, ChatOutputDto> kafkaTemplate;

    public void enviarMensajeAKafka(ChatOutputDto outputDto) {
        // Enviamos el DTO al tópico "chat-topic"
        kafkaTemplate.send("chat-topic", outputDto);
    }
}
