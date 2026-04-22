package com.vmr.oaevents.model.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInputDto {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email no valido")
    private String email;

    @NotBlank(message = "El email es obligatorio")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$", message = "La contraseña debe tener al menos 8 caracteres, una letra y un numero")
    private String contrasena;

    @NotBlank(message = "El email es obligatorio")
    private String nombre;

    @NotBlank(message = "El telefono es obligatorio")
    @Pattern(regexp = "^[679]\\d{8}$", message = "El formato de telefono es incorrecto")
    private String telefono;

    @NotNull(message = "El id de rol es obligatorio")
    private Long rol_id;

}
