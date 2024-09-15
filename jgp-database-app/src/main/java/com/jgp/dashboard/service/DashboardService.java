package com.jgp.dashboard.service;

import com.jgp.dashboard.dto.DataPointDto;
import com.jgp.dashboard.dto.HighLevelSummaryDto;
import com.jgp.dashboard.dto.SeriesDataPointDto;

import java.util.List;

public interface DashboardService {

    HighLevelSummaryDto getHighLevelSummary();

    List<DataPointDto> getLoanDisbursedByGenderSummary(Long partnerId);

    List<DataPointDto> getBusinessOwnersTrainedByGenderSummary(Long partnerId);

    List<DataPointDto> getLoanDisbursedByPipelineSourceSummary(Long partnerId);

    List<DataPointDto> getLoansDisbursedByQualitySummary(Long partnerId);

    List<SeriesDataPointDto> getTaNeedsByGenderSummary(Long partnerId);

    List<DataPointDto> getTaTrainingBySectorSummary(Long partnerId);

    List<SeriesDataPointDto> getTrainingByPartnerByGenderSummary();

    List<SeriesDataPointDto> getLastThreeYearsAccessedLoanPerPartnerSummary();

    List<SeriesDataPointDto> getLoansAccessedVsOutStandingByPartnerSummary();
}
