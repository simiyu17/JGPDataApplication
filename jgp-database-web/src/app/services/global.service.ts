import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({ providedIn: 'root' })
export class GlobalService {
  //API_HOST = '68.183.21.53';
  API_HOST = 'localhost';
  API_PORT = '8082';
  BASE_API_URL: string = `http://${this.API_HOST}:${this.API_PORT}/jgp-app/api/v1`;  
 
  HTTP_OPTIONS = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  constructor(public _snackBar: MatSnackBar) { }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 3000,
      verticalPosition: 'top'
    });
  }

}