package com.jgp.dashboard.api;

import com.jgp.dashboard.dto.DataPointDto;
import com.jgp.dashboard.dto.HighLevelSummaryDto;
import com.jgp.dashboard.dto.SeriesDataPointDto;
import com.jgp.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("api/v1/reports")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("high-level-summary")
    public ResponseEntity<HighLevelSummaryDto> getHighLevelSummary(){
        return new ResponseEntity<>(this.dashboardService.getHighLevelSummary(), HttpStatus.OK);
    }

    @GetMapping("loans-disbursed-by-gender")
    public ResponseEntity<List<DataPointDto>> getLoansDisbursedByGenderSummary(@RequestParam(value = "partner-id", required = false) Long partnerId){
        return new ResponseEntity<>(this.dashboardService.getLoanDisbursedByGenderSummary(partnerId), HttpStatus.OK);
    }

    @GetMapping("businesses-trained-by-gender")
    public ResponseEntity<List<DataPointDto>> getBusinessesByGenderSummary(@RequestParam(value = "partner-id", required = false) Long partnerId){
        return new ResponseEntity<>(this.dashboardService.getBusinessOwnersTrainedByGenderSummary(partnerId), HttpStatus.OK);
    }

    @GetMapping("loans-disbursed-by-pipeline")
    public ResponseEntity<List<DataPointDto>> getLoansDisbursedByPipelineSummary(@RequestParam(value = "partner-id", required = false) Long partnerId){
        return new ResponseEntity<>(this.dashboardService.getLoanDisbursedByPipelineSourceSummary(partnerId), HttpStatus.OK);
    }

    @GetMapping("loans-disbursed-by-quality")
    public ResponseEntity<List<DataPointDto>> getLoansDisbursedByQualitySummary(@RequestParam(value = "partner-id", required = false) Long partnerId){
        return new ResponseEntity<>(this.dashboardService.getLoansDisbursedByQualitySummary(partnerId), HttpStatus.OK);
    }

    @GetMapping("ta-needs-by-gender")
    public ResponseEntity<List<SeriesDataPointDto>> getTaNeedsByGenderSummary(@RequestParam(value = "partner-id", required = false) Long partnerId){
        return new ResponseEntity<>(this.dashboardService.getTaNeedsByGenderSummary(partnerId), HttpStatus.OK);
    }
}
