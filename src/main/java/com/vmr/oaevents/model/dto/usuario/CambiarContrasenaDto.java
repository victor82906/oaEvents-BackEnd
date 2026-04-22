package com.vmr.oaevents.model.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CambiarContrasenaDto {

    @NotBlank(message = "La contraseña es obligatoria")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$", message = "La contraseña debe tener al menos 8 caracteres, una letra y un numero")
    private String contrasenaActual;

    @NotBlank(message = "La contraseña es obligatoria")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$", message = "La contraseña debe tener al menos 8 caracteres, una letra y un numero")
    private String contrasena;

}
