package com.vmr.oaevents.model.dto.localidad;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocalidadInputDto {

    @NotBlank(message = "La fila es obligatoria")
    private String fila;

    @NotBlank(message = "El numero es obligatorio")
    private String numero;

    @NotNull(message = "La posicion x es obligatoria")
    private Double posX;

    @NotNull(message = "La posicion y es obligatoria")
    private Double posY;

    @NotNull(message = "El id de zona es obligatorio")
    private Long zona_id;

}
