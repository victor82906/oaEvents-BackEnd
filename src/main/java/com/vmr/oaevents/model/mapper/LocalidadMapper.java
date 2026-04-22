package com.vmr.oaevents.model.mapper;

import com.vmr.oaevents.model.Entrada;
import com.vmr.oaevents.model.Localidad;
import com.vmr.oaevents.model.dto.localidad.LocalidadInputDto;
import com.vmr.oaevents.model.dto.localidad.LocalidadOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocalidadMapper {

    @Mapping(source = "zona_id", target = "zona.id")
    Localidad toEntity(LocalidadInputDto localidadInputDto);

    @Mapping(source = "zona.id", target = "zona_id")
    @Mapping(source = "entradas", target = "entradas_id")
    LocalidadOutputDto toDto(Localidad localidad);

    default Long mapEntradaToId(Entrada e){
        return e.getId();
    }
}
