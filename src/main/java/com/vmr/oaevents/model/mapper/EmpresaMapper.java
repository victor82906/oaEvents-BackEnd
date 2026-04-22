package com.vmr.oaevents.model.mapper;

import com.vmr.oaevents.model.Empresa;
import com.vmr.oaevents.model.Evento;
import com.vmr.oaevents.model.dto.empresa.EmpresaInputDto;
import com.vmr.oaevents.model.dto.empresa.EmpresaOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {

    @Mapping(target = "activa", ignore = true)
    Empresa toEntity(EmpresaInputDto empresaInputDto);

    @Mapping(source = "eventos", target = "eventos_id")
    EmpresaOutputDto toDto(Empresa empresa);

    default Long mapEventoToId(Evento e){
        return e.getId();
    }

}
