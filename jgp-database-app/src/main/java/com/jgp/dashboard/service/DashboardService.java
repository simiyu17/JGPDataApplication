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

    List<DataPointDto> getLoanDisbursedByPipelineSourceSummary(Long partnerId);

    List<DataPointDto> getLoansDisbursedByQualitySummary(Long partnerId);

    List<SeriesDataPointDto> getTaNeedsByGenderSummary(Long partnerId);

    List<DataPointDto> getTaTrainingBySectorSummary(Long partnerId);

    List<SeriesDataPointDto> getTrainingByPartnerByGenderSummary();

    List<SeriesDataPointDto> getLastThreeYearsAccessedLoanPerPartnerSummary();

    List<SeriesDataPointDto> getLoansAccessedVsOutStandingByPartnerSummary();
}
