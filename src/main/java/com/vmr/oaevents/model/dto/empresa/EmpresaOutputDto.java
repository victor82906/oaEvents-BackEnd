package com.vmr.oaevents.model.dto.empresa;

import com.vmr.oaevents.model.dto.rol.RolOutputDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmpresaOutputDto {

    private Long id;
    private String email;
    private String nombre;
    private String telefono;
    private RolOutputDto rol;
    private String cif;
    private boolean activa;
    private List<Long> eventos_id;

}
