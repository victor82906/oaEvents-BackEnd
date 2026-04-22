package com.vmr.oaevents.model.mapper;

import com.vmr.oaevents.model.Rol;
import com.vmr.oaevents.model.dto.rol.RolInputDto;
import com.vmr.oaevents.model.dto.rol.RolOutputDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolMapper {

    Rol toEntity(RolInputDto rolInputDto);

    RolOutputDto toDto(Rol rol);

}
