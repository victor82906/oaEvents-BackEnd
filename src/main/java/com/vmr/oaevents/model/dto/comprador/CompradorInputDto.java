package com.vmr.oaevents.model.dto.comprador;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompradorInputDto {

    @NotBlank(message = "El dni es obligatorio")
    @Pattern(regexp = "^[0-9]{8}[A-Z]$", message = "Formato de DNI incorrecto")
    private String dni;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

}
