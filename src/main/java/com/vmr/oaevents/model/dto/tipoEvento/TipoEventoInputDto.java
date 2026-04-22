package com.vmr.oaevents.model.dto.tipoEvento;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoEventoInputDto {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

}
