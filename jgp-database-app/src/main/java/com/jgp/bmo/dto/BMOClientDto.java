package com.jgp.bmo.dto;

import java.time.LocalDate;

public record BMOClientDto(
         Long partnerId,

         Long clientId,

         String clientName,

         LocalDate dateFormSubmitted,

         Boolean isApplicantEligible,

         Integer tasAttended,

         Integer taSessionsAttended,

         Boolean isRecommendedForFinance,

         LocalDate decisionDate,

         String fiBusinessReferred,

         LocalDate dateRecordedByPartner,

         LocalDate dateRecordedToJGPDB
) {}
