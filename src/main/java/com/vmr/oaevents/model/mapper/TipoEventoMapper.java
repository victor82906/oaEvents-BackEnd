package com.vmr.oaevents.model.mapper;

import com.vmr.oaevents.model.Evento;
import com.vmr.oaevents.model.TipoEvento;
import com.vmr.oaevents.model.dto.tipoEvento.TipoEventoInputDto;
import com.vmr.oaevents.model.dto.tipoEvento.TipoEventoOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TipoEventoMapper {

    TipoEvento toEntity(TipoEventoInputDto tipoEventoInputDto);

    @Mapping(source = "eventos", target = "eventos_id")
    TipoEventoOutputDto toDto(TipoEvento tipoEvento);

    default Long mapEventoToId(Evento e){
        return e.getId();
    }

}
