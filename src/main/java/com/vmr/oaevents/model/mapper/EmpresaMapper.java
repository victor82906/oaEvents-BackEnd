package com.vmr.oaevents.model.mapper;

import com.vmr.oaevents.model.Empresa;
import com.vmr.oaevents.model.Evento;
import com.vmr.oaevents.model.dto.empresa.EmpresaInputDto;
import com.vmr.oaevents.model.dto.empresa.EmpresaOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RolMapper.class})
public interface EmpresaMapper {

    @Mapping(target = "activa", ignore = true)
    Empresa toEntity(EmpresaInputDto empresaInputDto);

    @Mapping(source = "eventos", target = "eventos_id")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(source = "rol", target = "rol")
    EmpresaOutputDto toDto(Empresa empresa);

    default Long mapEventoToId(Evento e){
        return e.getId();
    }

}
