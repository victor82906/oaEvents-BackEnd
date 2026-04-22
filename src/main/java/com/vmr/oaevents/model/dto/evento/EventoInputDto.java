package com.vmr.oaevents.model.dto.evento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventoInputDto {

    @NotBlank(message = "El titulo es obligatorio")
    private String titulo;

    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fecha;

    private Boolean aceptado;

    @NotNull(message = "El id de tipo de evento es obligatorio")
    private Long tipoEvento_id;

    @NotNull(message = "El id de empresa es obligatorio")
    private Long empresa_id;

}
