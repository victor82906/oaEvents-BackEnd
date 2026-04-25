package com.vmr.oaevents.model.dto.evento;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class EventoOutputDto {

    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fecha;
    private boolean aceptado;
    private String foto;
    private Long tipoEvento_id;
    private Long empresa_id;
    private List<Long> zonasEvento_id;

}
