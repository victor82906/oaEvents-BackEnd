package com.vmr.oaevents.model.mapper;

import com.vmr.oaevents.model.Qr;
import com.vmr.oaevents.model.dto.qr.QrInputDto;
import com.vmr.oaevents.model.dto.qr.QrOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QrMapper {

    Qr toEntity(QrInputDto qrInputDto);

    @Mapping(source = "entrada.id", target = "entrada_id")
    QrOutputDto toDto(Qr qr);

}
