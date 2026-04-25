package com.vmr.oaevents.model.dto.entrada;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EntradaCompraInputDto {

    @NotNull(message = "Debe comprar minimo una entrada")
    private List<Long> localidad_ids;

    @NotNull(message = "El id de zonaEvento es obligatorio")
    private Long zonaEvento_id;

    @NotNull(message = "El id de evento es obligatorio")
    private Long evento_id;

    @NotBlank(message = "La tarjeta de credito es obligatoria")
    private String tarjetaCredito;

    @NotBlank(message = "El nombre del comprador es obligatorio")
    private String nombreComprador;

    @NotBlank(message = "El email del comprador es obligatorio")
    @Email(message = "Email no valido")
    private String emailComprador;

    @NotBlank(message = "El dni del comprador es obligatorio")
    @Pattern(regexp = "^[0-9]{8}[A-Z]$", message = "Formato de DNI incorrecto")
    private String dniComprador;

}
