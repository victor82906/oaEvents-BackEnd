package com.vmr.oaevents.model.dto.recinto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecintoInputDto {

    @NotBlank(message = "La ubicacion es obligatoria")
    private String ubicacion;

    @NotBlank(message = "El mapa es obligatorio")
    private String mapa;

}
