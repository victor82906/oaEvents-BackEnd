package com.vmr.oaevents.model.mapper;

import com.vmr.oaevents.model.Entrada;
import com.vmr.oaevents.model.ZonaEvento;
import com.vmr.oaevents.model.dto.zonaEvento.ZonaEventoInputDto;
import com.vmr.oaevents.model.dto.zonaEvento.ZonaEventoOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ZonaEventoMapper {

    @Mapping(source = "evento_id", target = "evento.id")
    @Mapping(source = "zona_id", target = "zona.id")
    ZonaEvento toEntity(ZonaEventoInputDto zonaEventoInputDto);

    @Mapping(source = "evento.id", target = "evento_id")
    @Mapping(source = "zona.id", target = "zona_id")
    @Mapping(source = "entradas", target = "entradas_id")
    ZonaEventoOutputDto toDto(ZonaEvento ZonaEvento);

    default Long mapEntradaToId(Entrada e){
        return e.getId();
    }

}
