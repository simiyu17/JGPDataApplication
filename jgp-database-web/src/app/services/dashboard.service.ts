import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GlobalService } from './global.service';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HighLevelSummaryDto } from '../pages/dashboard/dto/highLevelSummaryDto';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

    constructor(private httpClient: HttpClient, private globalService: GlobalService, private router: Router) { }


    getHighLevelSummary(): Observable<any> {
        return this.httpClient.get(`${this.globalService.BASE_API_URL}/reports/high-level-summary`);
      }
}