package com.jgp.participant.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ParticipantDto(
         String businessName,

         String jgpId,

         String phoneNumber,

         String ownerGender,

         Integer ownerAge,

         String businessLocation,

         String industrySector,

         String businessSegment,

         Boolean isBusinessRegistered,

         String registrationNumber,

         Boolean hasBMOMembership,

         String bmoMembership,

         BigDecimal bestMonthlyRevenue,

         BigDecimal worstMonthlyRevenue,

         Integer totalRegularEmployees,

         Integer youthRegularEmployees,

         Integer totalCasualEmployees,

         Integer youthCasualEmployees,

         String sampleRecords,

         String taNeeds,

         String personWithDisability,

        String refugeeStatus
) {
}
