package com.vmr.oaevents.model.mapper;

import com.vmr.oaevents.model.Localidad;
import com.vmr.oaevents.model.Zona;
import com.vmr.oaevents.model.ZonaEvento;
import com.vmr.oaevents.model.dto.zona.ZonaInputDto;
import com.vmr.oaevents.model.dto.zona.ZonaOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ZonaMapper {

    @Mapping(source = "recinto_id", target = "recinto.id")
    Zona toEntity(ZonaInputDto zonaInputDto);

    @Mapping(source = "recinto.id", target = "recinto_id")
    @Mapping(source = "localidades", target = "localidades_id")
    @Mapping(source = "zonaEventos", target = "zonaEventos_id")
    ZonaOutputDto toDto(Zona zona);

    default Long mapLocalidadToId(Localidad l){
        return l.getId();
    }

    default Long mapZonaEventoToId(ZonaEvento ze){
        return ze.getId();
    }

}
