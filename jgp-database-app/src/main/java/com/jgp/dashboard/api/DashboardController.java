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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("api/v1/reports")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("high-level-summary")
    public ResponseEntity<HighLevelSummaryDto> getHighLevelSummary(@RequestParam(value = "partner-id", required = false) Long partnerId,
                                                                   @RequestParam(value = "from-date", required = false) LocalDate fromDate,
                                                                   @RequestParam(value = "to-date", required = false) LocalDate toDate){
        return new ResponseEntity<>(this.dashboardService.getHighLevelSummary(fromDate, toDate, partnerId), HttpStatus.OK);
    }

    @GetMapping("loans-disbursed-by-gender")
    public ResponseEntity<List<DataPointDto>> getLoansDisbursedByGenderSummary(@RequestParam(value = "partner-id", required = false) Long partnerId,
                                                                               @RequestParam(value = "from-date", required = false) LocalDate fromDate,
                                                                               @RequestParam(value = "to-date", required = false) LocalDate toDate){
        return new ResponseEntity<>(this.dashboardService.getLoanDisbursedByGenderSummary(fromDate, toDate, partnerId), HttpStatus.OK);
    }

    @GetMapping("businesses-trained-by-gender")
    public ResponseEntity<List<DataPointDto>> getBusinessesByGenderSummary(@RequestParam(value = "partner-id", required = false) Long partnerId,
                                                                           @RequestParam(value = "from-date", required = false) LocalDate fromDate,
                                                                           @RequestParam(value = "to-date", required = false) LocalDate toDate){
        return new ResponseEntity<>(this.dashboardService.getBusinessOwnersTrainedByGenderSummary(fromDate, toDate, partnerId), HttpStatus.OK);
    }

    @GetMapping("loans-disbursed-by-pipeline")
    public ResponseEntity<List<DataPointDto>> getLoansDisbursedByPipelineSummary(@RequestParam(value = "partner-id", required = false) Long partnerId,
                                                                                 @RequestParam(value = "from-date", required = false) LocalDate fromDate,
                                                                                 @RequestParam(value = "to-date", required = false) LocalDate toDate){
        return new ResponseEntity<>(this.dashboardService.getLoanDisbursedByPipelineSourceSummary(fromDate, toDate, partnerId), HttpStatus.OK);
    }

    @GetMapping("loans-disbursed-by-quality")
    public ResponseEntity<List<DataPointDto>> getLoansDisbursedByQualitySummary(@RequestParam(value = "partner-id", required = false) Long partnerId,
                                                                                @RequestParam(value = "from-date", required = false) LocalDate fromDate,
                                                                                @RequestParam(value = "to-date", required = false) LocalDate toDate){
        return new ResponseEntity<>(this.dashboardService.getLoansDisbursedByQualitySummary(fromDate, toDate, partnerId), HttpStatus.OK);
    }

    @GetMapping("ta-needs-by-gender")
    public ResponseEntity<List<SeriesDataPointDto>> getTaNeedsByGenderSummary(@RequestParam(value = "partner-id", required = false) Long partnerId,
                                                                              @RequestParam(value = "from-date", required = false) LocalDate fromDate,
                                                                              @RequestParam(value = "to-date", required = false) LocalDate toDate){
        return new ResponseEntity<>(this.dashboardService.getTaNeedsByGenderSummary(fromDate, toDate, partnerId), HttpStatus.OK);
    }

    @GetMapping("ta-training-by-sector")
    public ResponseEntity<List<DataPointDto>> getTaTrainingBySectorSummary(@RequestParam(value = "partner-id", required = false) Long partnerId,
                                                                           @RequestParam(value = "from-date", required = false) LocalDate fromDate,
                                                                           @RequestParam(value = "to-date", required = false) LocalDate toDate){
        return new ResponseEntity<>(this.dashboardService.getTaTrainingBySectorSummary(fromDate, toDate, partnerId), HttpStatus.OK);
    }

    @GetMapping("ta-training-by-segment")
    public ResponseEntity<List<DataPointDto>> getTaTrainingBySegmentSummary(@RequestParam(value = "partner-id", required = false) Long partnerId,
                                                                           @RequestParam(value = "from-date", required = false) LocalDate fromDate,
                                                                           @RequestParam(value = "to-date", required = false) LocalDate toDate){
        return new ResponseEntity<>(this.dashboardService.getTaTrainingBySegmentSummary(fromDate, toDate, partnerId), HttpStatus.OK);
    }

    @GetMapping("training-by-partner-by-gender")
    public ResponseEntity<List<SeriesDataPointDto>> getTrainingByPartnerByGenderSummary(@RequestParam(value = "from-date", required = false) LocalDate fromDate,
                                                                                        @RequestParam(value = "to-date", required = false) LocalDate toDate){
        return new ResponseEntity<>(this.dashboardService.getTrainingByPartnerByGenderSummary(fromDate, toDate), HttpStatus.OK);
    }

    @GetMapping("loan-accessed-per-partner-for-last-three-years")
    public ResponseEntity<List<SeriesDataPointDto>> getLastThreeYearsAccessedLoanPerPartnerSummary(@RequestParam(value = "partner-id", required = false) Long partnerId){
        return new ResponseEntity<>(this.dashboardService.getLastThreeYearsAccessedLoanPerPartnerSummary(partnerId), HttpStatus.OK);
    }

    @GetMapping("loans-accessed-vs-out-standing-per-partner")
    public ResponseEntity<List<SeriesDataPointDto>> getLoansAccessedVsOutStandingByPartnerSummary(@RequestParam(value = "partner-id", required = false) Long partnerId,
                                                                                                  @RequestParam(value = "from-date", required = false) LocalDate fromDate,
                                                                                                  @RequestParam(value = "to-date", required = false) LocalDate toDate){
        return new ResponseEntity<>(this.dashboardService.getLoansAccessedVsOutStandingByPartnerSummary(fromDate, toDate, partnerId), HttpStatus.OK);
    }
}
