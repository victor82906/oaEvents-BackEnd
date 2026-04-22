package com.vmr.oaevents.model.dto.rol;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolInputDto {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

}
