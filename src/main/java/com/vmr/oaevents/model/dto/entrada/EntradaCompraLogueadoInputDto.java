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
public class EntradaCompraLogueadoInputDto {

    @NotNull(message = "Debe comprar minimo una entrada")
    private List<Long> localidad_ids;

    @NotNull(message = "El id de zonaEvento es obligatorio")
    private Long zonaEvento_id;

    @NotNull(message = "El id de evento es obligatorio")
    private Long evento_id;

    @NotBlank(message = "La tarjeta de credito es obligatoria")
    private String tarjetaCredito;

}
