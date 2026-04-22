package com.vmr.oaevents.model.dto.zonaEvento;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZonaEventoInputDto {

    @NotNull(message = "El precio es obligatorio")
    private Double precio;

    @NotNull(message = "El campo habilitada es obligatorio")
    private Boolean habilitada;

    @NotNull(message = "El id de evento es obligatorio")
    private Long evento_id;

    @NotNull(message = "El id de zona es obligatorio")
    private Long zona_id;

}
