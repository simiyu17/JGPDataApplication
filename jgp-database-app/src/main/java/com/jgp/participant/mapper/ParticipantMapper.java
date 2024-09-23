package com.jgp.participant.mapper;

import com.jgp.participant.domain.Participant;
import com.jgp.participant.dto.ParticipantDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ParticipantMapper {


    @Mapping(target = "businessName", expression = "java(participant.getBusinessName())")
    @Mapping(target = "jgpId", expression = "java(participant.getJgpId())")
    @Mapping(target = "phoneNumber", expression = "java(participant.getPhoneNumber())")
    @Mapping(target = "ownerGender", expression = "java(null != participant.getOwnerGender() ? participant.getOwnerGender().getName() : null)")
    @Mapping(target = "ownerAge", expression = "java(participant.getOwnerAge())")
    @Mapping(target = "businessLocation", expression = "java(participant.getBusinessLocation())")
    @Mapping(target = "industrySector", expression = "java(participant.getIndustrySector())")
    @Mapping(target = "businessSegment", expression = "java(participant.getBusinessSegment())")
    @Mapping(target = "isBusinessRegistered", ignore = true)
    @Mapping(target = "registrationNumber", expression = "java(participant.getRegistrationNumber())")
    @Mapping(target = "hasBMOMembership", ignore = true)
    @Mapping(target = "bmoMembership", expression = "java(participant.getBmoMembership())")
    @Mapping(target = "bestMonthlyRevenue", expression = "java(participant.getBestMonthlyRevenue())")
    @Mapping(target = "worstMonthlyRevenue", expression = "java(participant.getWorstMonthlyRevenue())")
    @Mapping(target = "totalRegularEmployees", expression = "java(participant.getTotalRegularEmployees())")
    @Mapping(target = "youthRegularEmployees", expression = "java(participant.getYouthRegularEmployees())")
    @Mapping(target = "totalCasualEmployees", expression = "java(participant.getTotalCasualEmployees())")
    @Mapping(target = "youthCasualEmployees", expression = "java(participant.getYouthCasualEmployees())")
    @Mapping(target = "sampleRecords", expression = "java(participant.getSampleRecords())")
    @Mapping(target = "taNeeds", expression = "java(participant.getTaNeeds())")
    @Mapping(target = "bmoClientDtos", ignore = true)
    @Mapping(target = "loanDtos", ignore = true)
    ParticipantDto toDto(Participant participant);

    List<ParticipantDto> toDto(List<Participant> participants);
}
