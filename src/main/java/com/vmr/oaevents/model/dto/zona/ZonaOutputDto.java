package com.vmr.oaevents.model.dto.zona;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZonaOutputDto {

    private Long id;
    private String numero;
    private String coordenadas;
    private String puertaEntrada;
    private Boolean pista;
    private Long recinto_id;

}
