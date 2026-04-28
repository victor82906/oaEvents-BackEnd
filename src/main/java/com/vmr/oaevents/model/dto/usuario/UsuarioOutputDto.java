package com.vmr.oaevents.model.dto.usuario;

import com.vmr.oaevents.model.dto.rol.RolOutputDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioOutputDto {

    private Long id;
    private String email;
    private String nombre;
    private String telefono;
    private RolOutputDto rol;

}
