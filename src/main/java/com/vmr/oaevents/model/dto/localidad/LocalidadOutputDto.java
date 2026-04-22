package com.vmr.oaevents.model.dto.localidad;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LocalidadOutputDto {

    private Long id;
    private String fila;
    private String numero;
    private double posX;
    private double posY;
    private Long zona_id;
    private List<Long> entradas_id;

}
