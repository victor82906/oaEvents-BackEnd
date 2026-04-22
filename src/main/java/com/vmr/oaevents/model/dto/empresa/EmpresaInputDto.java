package com.vmr.oaevents.model.dto.empresa;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaInputDto {

    @NotBlank(message = "El cif es obligatorio")
    private String cif;

    private Boolean activa;

}
