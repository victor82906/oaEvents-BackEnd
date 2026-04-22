package com.vmr.oaevents.model.mapper;

import com.vmr.oaevents.model.Evento;
import com.vmr.oaevents.model.ZonaEvento;
import com.vmr.oaevents.model.dto.evento.EventoInputDto;
import com.vmr.oaevents.model.dto.evento.EventoOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventoMapper {

    @Mapping(source = "tipoEvento_id", target = "tipoEvento.id")
    @Mapping(source = "empresa_id", target = "empresa.id")
    @Mapping(target = "aceptado", ignore = true)
    Evento toEntity(EventoInputDto eventoInputDto);

    @Mapping(source = "tipoEvento.id", target = "tipoEvento_id")
    @Mapping(source = "empresa.id", target = "empresa_id")
    @Mapping(source = "zonasEvento", target = "zonasEvento_id")
    EventoOutputDto toDto(Evento evento);

    default Long mapZonaEventoToId(ZonaEvento ze){
        return ze.getId();
    }

}
