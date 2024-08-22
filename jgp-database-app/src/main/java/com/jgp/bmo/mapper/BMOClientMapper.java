package com.jgp.bmo.mapper;

import com.jgp.bmo.domain.BMOClientData;
import com.jgp.bmo.dto.BMOClientDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface BMOClientMapper {

    @Mapping(target = "partnerId", expression = "java(null != bmoClientData.getPartner() ? bmoClientData.getPartner().getId() : null)")
    @Mapping(target = "clientId", expression = "java(null != bmoClientData.getClient() ? bmoClientData.getClient().getId() : null)")
    @Mapping(target = "clientName", expression = "java(null != bmoClientData.getClient() ? bmoClientData.getClient().getBusinessName() : null)")
    BMOClientDto toDto(BMOClientData bmoClientData);

    List<BMOClientDto> toDto(List<BMOClientData> bmoClientDataList);
}
