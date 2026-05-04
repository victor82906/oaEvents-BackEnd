package com.vmr.oaevents.model.dto.zonaEvento;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ZonaEventoOutputDto {

    private Long id;
    private double precio;
    private Boolean habilitada;
    private Long evento_id;
    private Long zona_id;
    private List<Long> entradas_id;

}
