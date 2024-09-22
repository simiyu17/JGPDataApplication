import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GlobalService } from '../shared/global.service';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataUploadService {

    constructor(private httpClient: HttpClient, private gs: GlobalService) { }


    uploadDataTemplate(file: File, templateName: string): Observable<any> {
        const formData = new FormData();
        formData.append('excelFile', file, file.name);
        if(templateName.toLowerCase().includes('bmo')){
            return this.httpClient.post(`${this.gs.BASE_API_URL}/bmos/upload-template`, formData);
        }else if(templateName.toLowerCase().includes('loan')){
            return this.httpClient.post(`${this.gs.BASE_API_URL}/loans/upload-template`, formData);
        }
        return of(null);
      }

}