import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GlobalService } from './global.service';
import { JwtHelperService } from '@auth0/angular-jwt';
import { UserRole } from '../dto/UserRole';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

    constructor(private httpClient: HttpClient, private globalService: GlobalService, private router: Router) { }


    getAvailableClients(): Observable<any> {
        return this.httpClient.get(`${this.globalService.BASE_API_URL}/clients`);
      }
}