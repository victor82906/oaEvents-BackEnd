package com.vmr.oaevents.model.mapper;

import com.vmr.oaevents.model.Comprador;
import com.vmr.oaevents.model.Entrada;
import com.vmr.oaevents.model.dto.comprador.CompradorInputDto;
import com.vmr.oaevents.model.dto.comprador.CompradorOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompradorMapper {

    Comprador toEntity(CompradorInputDto compradorInputDto);

    @Mapping(source = "entradas", target = "entradas_id")
    CompradorOutputDto toDto(Comprador comprador);

    default Long mapEntradaToId(Entrada e){
        return e.getId();
    }

}
