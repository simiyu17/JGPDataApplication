import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GlobalService } from '../shared/global.service';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoanService {

    constructor(private httpClient: HttpClient, private gs: GlobalService, private router: Router) { }


    uploadLendingData(file: File): Observable<any> {
        const formData = new FormData();
        formData.append('excelFile', file, file.name);
        return this.httpClient.post(`${this.gs.BASE_API_URL}/loans/upload-template`, formData);
      }

      getAvailableNewLendingData(): Observable<any> {
        return this.httpClient.get(`${this.gs.BASE_API_URL}/loans`);
      }
}