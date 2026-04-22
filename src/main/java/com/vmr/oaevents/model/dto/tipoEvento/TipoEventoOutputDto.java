package com.vmr.oaevents.model.dto.tipoEvento;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TipoEventoOutputDto {

    private Long id;
    private String nombre;
    private List<Long> eventos_id;

}
