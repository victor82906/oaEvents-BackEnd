package com.vmr.oaevents.model.dto.zona;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ZonaOutputDto {

    private Long id;
    private String coordenadas;
    private String puertaEntrada;
    private Long recinto_id;
    private List<Long> localidades_id;
    private List<Long> zonaEventos_id;

}
