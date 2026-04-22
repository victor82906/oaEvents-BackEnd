package com.vmr.oaevents.model.mapper;

import com.vmr.oaevents.model.Validador;
import com.vmr.oaevents.model.dto.validador.ValidadorInputDto;
import com.vmr.oaevents.model.dto.validador.ValidadorOutputDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ValidadorMapper {

    Validador toEntity(ValidadorInputDto validadorInputDto);

    ValidadorOutputDto toDto(Validador validador);

}
