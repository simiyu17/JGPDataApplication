package com.jgp.dashboard.service;

import com.jgp.dashboard.dto.DataPointDto;
import com.jgp.dashboard.dto.HighLevelSummaryDto;
import com.jgp.dashboard.dto.SeriesDataPointDto;

import java.time.LocalDate;
import java.util.List;

public interface DashboardService {

    HighLevelSummaryDto getHighLevelSummary(LocalDate fromDate, LocalDate toDate, Long partnerId);

    List<DataPointDto> getLoanDisbursedByGenderSummary(LocalDate fromDate, LocalDate toDate, Long partnerId);

    List<DataPointDto> getBusinessOwnersTrainedByGenderSummary(LocalDate fromDate, LocalDate toDate, Long partnerId);

    List<DataPointDto> getLoanDisbursedByPipelineSourceSummary(LocalDate fromDate, LocalDate toDate, Long partnerId);

    List<DataPointDto> getLoansDisbursedByQualitySummary(LocalDate fromDate, LocalDate toDate, Long partnerId);

    List<SeriesDataPointDto> getTaNeedsByGenderSummary(LocalDate fromDate, LocalDate toDate, Long partnerId);

    List<DataPointDto> getTaTrainingBySectorSummary(LocalDate fromDate, LocalDate toDate, Long partnerId);

    List<SeriesDataPointDto> getTrainingByPartnerByGenderSummary(LocalDate fromDate, LocalDate toDate);

    List<SeriesDataPointDto> getLastThreeYearsAccessedLoanPerPartnerSummary(Long partnerId);

    List<SeriesDataPointDto> getLoansAccessedVsOutStandingByPartnerSummary(LocalDate fromDate, LocalDate toDate, Long partnerId);
}
