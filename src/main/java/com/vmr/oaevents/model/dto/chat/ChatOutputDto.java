package com.vmr.oaevents.model.dto.chat;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatOutputDto {

    private Long id;
    private String mensaje;
    private LocalDateTime fecha;
    private Long emisor_id;
    private Long receptor_id;

}
