package com.vmr.oaevents.model.dto.recinto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecintoOutputDto {

    private String ubicacion;
    private String mapa;
    private List<Long> zonas_id;

}
