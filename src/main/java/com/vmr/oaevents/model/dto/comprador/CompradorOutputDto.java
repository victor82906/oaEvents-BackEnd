package com.vmr.oaevents.model.dto.comprador;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompradorOutputDto {

    private String dni;
    private String apellidos;
    private List<Long> entradas_id;

}
