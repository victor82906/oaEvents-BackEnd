package com.vmr.oaevents.model.dto.validador;

import com.vmr.oaevents.model.dto.rol.RolOutputDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidadorOutputDto {

    private Long id;
    private String email;
    private String nombre;
    private String telefono;
    private RolOutputDto rol;
    private String dni;

}
