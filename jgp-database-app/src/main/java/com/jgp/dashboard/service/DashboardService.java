package com.jgp.dashboard.service;

import com.jgp.dashboard.dto.DataPointDto;
import com.jgp.dashboard.dto.HighLevelSummaryDto;
import java.util.List;

public interface DashboardService {

    HighLevelSummaryDto getHighLevelSummary();

    List<DataPointDto> getLoanDisbursedByGenderSummary(Long partnerId);

    List<DataPointDto> getBusinessOwnersTrainedByGenderSummary(Long partnerId);

    List<DataPointDto> getLoanDisbursedByPipelineSourceSummary(Long partnerId);
}
