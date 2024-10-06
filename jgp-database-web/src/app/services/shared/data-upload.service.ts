import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GlobalService } from '../shared/global.service';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataUploadService {

    constructor(private httpClient: HttpClient, private gs: GlobalService) { }


    uploadDataTemplate(file: File, templateName: string): Observable<any> {
        const formData = new FormData();
        formData.append('excelFile', file, file.name);
        if(templateName.toLowerCase().includes('bmo_import')){
            return this.httpClient.post(`${this.gs.BASE_API_URL}/bmos/upload-template`, formData);
        }else if(templateName.toLowerCase().includes('loan_import')){
            return this.httpClient.post(`${this.gs.BASE_API_URL}/loans/upload-template`, formData);
        }
        return of(null);
      }

      downloadDataTemplate(templateName: string): Observable<any> {
        if(templateName.toLowerCase().includes('bmo_import')){
            return this.httpClient.get(`${this.gs.BASE_API_URL}/bmos/template/download`, {
              responseType: 'arraybuffer',
              observe: 'response',
            });
        }else if(templateName.toLowerCase().includes('loan_import')){
            return this.httpClient.get(`${this.gs.BASE_API_URL}/loans/template/download`, {
              responseType: 'arraybuffer',
              observe: 'response',
            });
        }
        return of();
      }


      /**
   * Download file from API response
   *
   * @param res
   */
  downloadFileFromAPIResponse(res: any) {
    const headers = res.headers;
    const contentType = headers.get('Content-Type');
    const blob = new Blob([res.body], { type: contentType });
    const fileName = this.getFileNameFromHttpHeaders(headers);
    let fileLink = document.createElement('a');
    document.body.appendChild(fileLink);
    fileLink.style.display = 'none';
    const url = window.URL.createObjectURL(blob);
    fileLink.href = url;
    fileLink.download = fileName;
    fileLink.click();
    setTimeout(() => {
      window.URL.revokeObjectURL(url);
      document.body.removeChild(fileLink);
    }, 0);
  }

  /**
   * Get file name from HTTP headers
   * @param headers the HTTP headers
   * @returns the file name found in the headers
   */
  getFileNameFromHttpHeaders(headers: any): string {
    console.log(headers)
    const contentDispositionHeader = headers.get('Content-Disposition');
    let result = contentDispositionHeader.split(';')[1].trim().split('=')[1];
    return result.replace(/"/g, '');
  }

}