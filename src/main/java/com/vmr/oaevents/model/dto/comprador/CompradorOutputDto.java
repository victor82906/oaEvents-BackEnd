package com.vmr.oaevents.model.dto.comprador;

import com.vmr.oaevents.model.dto.rol.RolOutputDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompradorOutputDto {

    private Long id;
    private String email;
    private String nombre;
    private String telefono;
    private RolOutputDto rol;    private String dni;
    private String apellidos;
    private List<Long> entradas_id;

}
