package com.vmr.oaevents.model.mapper;

import com.vmr.oaevents.model.Entrada;
import com.vmr.oaevents.model.dto.entrada.EntradaInputDto;
import com.vmr.oaevents.model.dto.entrada.EntradaOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {QrMapper.class})
public interface EntradaMapper {

    @Mapping(source = "localidad_id", target = "localidad.id")
    @Mapping(source = "comprador_id", target = "zonaEvento.id")
    @Mapping(source = "comprador_id", target = "comprador.id")
    @Mapping(target = "fechaCompra", ignore = true)
    @Mapping(target = "nombreComprador", ignore = true)
    @Mapping(target = "emailComprador", ignore = true)
    @Mapping(target = "dniComprador", ignore = true)
    Entrada toEntity(EntradaInputDto entradaInputDto);

    @Mapping(source = "localidad.id", target = "localidad_id")
    @Mapping(source = "zonaEvento.id", target = "zonaEvento_id")
    @Mapping(source = "comprador.id", target = "comprador_id")
    EntradaOutputDto toDto(Entrada entrada);

}
