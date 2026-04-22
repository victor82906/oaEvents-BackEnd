package com.vmr.oaevents.model.mapper;

import com.vmr.oaevents.model.Recinto;
import com.vmr.oaevents.model.Zona;
import com.vmr.oaevents.model.dto.recinto.RecintoInputDto;
import com.vmr.oaevents.model.dto.recinto.RecintoOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecintoMapper {

    Recinto toEntity(RecintoInputDto recintoInputDto);

    @Mapping(source = "zonas", target = "zonas_id")
    RecintoOutputDto toDto(Recinto recinto);

    default Long mapZonaToId(Zona e){
        return e.getId();
    }

}
