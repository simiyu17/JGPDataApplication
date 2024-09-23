package com.jgp.participant.dto;

import com.jgp.bmo.dto.BMOClientDto;
import com.jgp.finance.dto.LoanDto;

import java.math.BigDecimal;
import java.util.List;

public class ParticipantResponseDto {
    String businessName;

    String jgpId;

    String phoneNumber;

    String ownerGender;

    Integer ownerAge;

    String businessLocation;

    String industrySector;

    String businessSegment;

    Boolean isBusinessRegistered;

    String registrationNumber;

    Boolean hasBMOMembership;

    String bmoMembership;

    BigDecimal bestMonthlyRevenue;

    BigDecimal worstMonthlyRevenue;

    Integer totalRegularEmployees,

    Integer youthRegularEmployees,

    Integer totalCasualEmployees,

    Integer youthCasualEmployees,

    String sampleRecords,

    String taNeeds,

    String personWithDisability,

    String refugeeStatus,
    List<BMOClientDto> bmoClientDtos,
    List<LoanDto> loanDtos
}
