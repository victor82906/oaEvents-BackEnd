package com.vmr.oaevents.model.dto.empresa;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmpresaOutputDto {

    private String cif;
    private boolean activa;
    private List<Long> eventos_id;

}
