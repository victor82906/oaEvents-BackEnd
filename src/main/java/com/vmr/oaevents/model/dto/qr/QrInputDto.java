package com.vmr.oaevents.model.dto.qr;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QrInputDto {

    @NotBlank(message = "El codigo es obligatorio")
    private String codigo;

    @NotBlank(message = "La foto es obligatoria")
    private String foto;

}
