package com.vmr.oaevents.model.dto.recinto;

import com.vmr.oaevents.model.dto.rol.RolOutputDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecintoOutputDto {

    private Long id;
    private String email;
    private String nombre;
    private String telefono;
    private RolOutputDto rol;
    private String ubicacion;
    private String mapa;
    private List<Long> zonas_id;

}
