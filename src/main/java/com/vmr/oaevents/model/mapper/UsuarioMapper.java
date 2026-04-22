package com.vmr.oaevents.model.mapper;

import com.vmr.oaevents.model.Usuario;
import com.vmr.oaevents.model.dto.usuario.UsuarioInputDto;
import com.vmr.oaevents.model.dto.usuario.UsuarioOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RolMapper.class})
public interface UsuarioMapper {

    @Mapping(source = "rol_id", target = "rol.id")
    Usuario toEntity(UsuarioInputDto usuarioInputDto);

    UsuarioOutputDto toDto(Usuario usuario);

}
