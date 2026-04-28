package com.vmr.oaevents.model.mapper;

import com.vmr.oaevents.model.Validador;
import com.vmr.oaevents.model.dto.validador.ValidadorInputDto;
import com.vmr.oaevents.model.dto.validador.ValidadorOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RolMapper.class})
public interface ValidadorMapper {

    Validador toEntity(ValidadorInputDto validadorInputDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(source = "rol", target = "rol")
    ValidadorOutputDto toDto(Validador validador);

}
