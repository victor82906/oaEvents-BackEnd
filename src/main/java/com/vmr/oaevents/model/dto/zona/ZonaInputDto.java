package com.vmr.oaevents.model.dto.zona;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZonaInputDto {

    @NotBlank(message = "Las coordenadas son obligatorias")
    private String coordenadas;

    @NotBlank(message = "La puerta de entrada es obligatoria")
    private String puertaEntrada;

    @NotNull(message = "El id de recinto es obligatorio")
    private Long recinto_id;

}
