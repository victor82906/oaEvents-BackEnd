package com.vmr.oaevents.model.mapper;

import com.vmr.oaevents.model.Recinto;
import com.vmr.oaevents.model.Zona;
import com.vmr.oaevents.model.dto.recinto.RecintoInputDto;
import com.vmr.oaevents.model.dto.recinto.RecintoOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RolMapper.class})
public interface RecintoMapper {

    Recinto toEntity(RecintoInputDto recintoInputDto);

    @Mapping(source = "zonas", target = "zonas_id")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(source = "rol", target = "rol")
    RecintoOutputDto toDto(Recinto recinto);

    default Long mapZonaToId(Zona e){
        return e.getId();
    }

}
