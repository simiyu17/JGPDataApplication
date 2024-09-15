package com.jgp.bmo.mapper;

import com.jgp.bmo.domain.BMOParticipantData;
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
    @Mapping(target = "tasAttended", expression = "java(null != bmoClientData.getTasAttended() ? bmoClientData.getTasAttended() : null)")
    @Mapping(target = "taSessionsAttended", expression = "java(null != bmoClientData.getTaSessionsAttended() ? bmoClientData.getTaSessionsAttended() : null)")
    @Mapping(target = "isRecommendedForFinance", expression = "java(null != bmoClientData.getIsRecommendedForFinance() ? bmoClientData.getIsRecommendedForFinance() : null)")
    @Mapping(target = "decisionDate", expression = "java(null != bmoClientData.getDecisionDate() ? bmoClientData.getDecisionDate() : null)")
    @Mapping(target = "dateFormSubmitted", expression = "java(null != bmoClientData.getDateFormSubmitted() ? bmoClientData.getDateFormSubmitted() : null)")
    BMOClientDto toDto(BMOParticipantData bmoClientData);

    List<BMOClientDto> toDto(List<BMOParticipantData> bmoClientDataList);
}
