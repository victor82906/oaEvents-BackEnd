package com.vmr.oaevents.model.mapper;

import com.vmr.oaevents.model.Comprador;
import com.vmr.oaevents.model.Entrada;
import com.vmr.oaevents.model.dto.comprador.CompradorInputDto;
import com.vmr.oaevents.model.dto.comprador.CompradorOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RolMapper.class})
public interface CompradorMapper {

    Comprador toEntity(CompradorInputDto compradorInputDto);

    @Mapping(source = "entradas", target = "entradas_id")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(source = "rol", target = "rol")
    CompradorOutputDto toDto(Comprador comprador);

    default Long mapEntradaToId(Entrada e){
        return e.getId();
    }

}
