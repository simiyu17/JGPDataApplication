import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { GlobalService } from '@services/shared/global.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

    constructor(private httpClient: HttpClient, private globalService: GlobalService, private router: Router) { }


    getHighLevelSummary(): Observable<any> {
      return this.httpClient.get(`${this.globalService.BASE_API_URL}/reports/high-level-summary`);
    }

    getLoansDisbursedByGenderSummary(): Observable<any> {
      return this.httpClient.get(`${this.globalService.BASE_API_URL}/reports/loans-disbursed-by-gender`);
    }

    getBusinessesTrainedByGenderSummary(): Observable<any> {
      return this.httpClient.get(`${this.globalService.BASE_API_URL}/reports/businesses-trained-by-gender`);
    }

    getLoansDisbursedByPipelineSummary(): Observable<any> {
      return this.httpClient.get(`${this.globalService.BASE_API_URL}/reports/loans-disbursed-by-pipeline`);
    }

    getLoansDisbursedByQualitySummary(): Observable<any> {
      return this.httpClient.get(`${this.globalService.BASE_API_URL}/reports/loans-disbursed-by-quality`);
    }

    getTaNeedsByGenderSummary(): Observable<any> {
      return this.httpClient.get(`${this.globalService.BASE_API_URL}/reports/ta-needs-by-gender`);
    }

    getTaTrainingBySectorSummary(): Observable<any> {
      return this.httpClient.get(`${this.globalService.BASE_API_URL}/reports/ta-training-by-sector`);
    }

    getTrainingByPartnerByGenderSummary(): Observable<any> {
      return this.httpClient.get(`${this.globalService.BASE_API_URL}/reports/training-by-partner-by-gender`);
    }

    getLastThreeYearsAccessedLoanPerPartnerSummary(): Observable<any> {
      return this.httpClient.get(`${this.globalService.BASE_API_URL}/reports/loan-accessed-per-partner-for-last-three-years`);
    }

    getLoansAccessedVsOutStandingByPartnerSummary(): Observable<any> {
      return this.httpClient.get(`${this.globalService.BASE_API_URL}/reports/loans-accessed-vs-out-standing-per-partner`);
    }

}