import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { GlobalService } from '@services/shared/global.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  AUTH_TOKEN_KEY: string = 'auth_token';
  USER_FULL_NAME: string = 'user_full_name';
  USER_EMAIL: string = 'user_email';
  USER_POSITION: string = 'user_position';
  USER_REGISTRATION: string = 'user_registration';
  FORCE_PASS_CHANGE: string = 'force_change_password';
  jwtService: JwtHelperService = new JwtHelperService();

  constructor(private httpClient: HttpClient, private globalService: GlobalService, private router: Router) { }

  login = (authRequest: {username: string, password: string}): Observable<any> => this.httpClient.post(`${this.globalService.BASE_API_URL}/users/authenticate`, JSON.stringify(authRequest))

  public setLocalStorageValue = (key : string, value: any): void => localStorage.setItem(key, value)

  public getLocalStorageValue = (key: string): string | null => localStorage.getItem(key);

  public getAuthToken = (): string | null => this.getLocalStorageValue(this.AUTH_TOKEN_KEY);

  public clearLocalStorageValue = (): void => localStorage.clear();
  
  public decodeAuthToken = (): any =>  this.getAuthToken() === null ? null : this.jwtService.decodeToken(this.getAuthToken()!);

  public storeUserDetails = (token?: string) : void => {
    if(token){
      this.setLocalStorageValue(this.AUTH_TOKEN_KEY, token);
      this.setLocalStorageValue(this.USER_FULL_NAME, this.decodeAuthToken()[this.USER_FULL_NAME]);
      this.setLocalStorageValue(this.USER_EMAIL, this.decodeAuthToken()[this.USER_EMAIL]);
      this.setLocalStorageValue(this.USER_POSITION, this.decodeAuthToken()[this.USER_POSITION]);
      this.setLocalStorageValue(this.USER_REGISTRATION, this.decodeAuthToken()[this.USER_REGISTRATION]);
      this.setLocalStorageValue(this.FORCE_PASS_CHANGE, this.decodeAuthToken()[this.FORCE_PASS_CHANGE]);
    }
  };

  public isAuthenticated = () : boolean => this.getAuthToken() !== null && !this.jwtService.isTokenExpired(this.getAuthToken());

  public isUserAdmin(): boolean {
    return (this.decodeAuthToken()['roles'] as Array<string>).includes('Admin');
  }

  userRedirection(): void {
    if(this.isAuthenticated()){
      this.router.navigateByUrl("/");
    }else {
      this.doLogout();
    }
  }

  redirectToChangePassword(): void{
    this.router.navigateByUrl("/change-password");
  }


  doLogout(): void {
    this.clearLocalStorageValue();
    this.router.navigateByUrl("/login");
  }

  currentUser(): any {
    return {
      name: this.getLocalStorageValue(this.USER_FULL_NAME),
      email: this.getLocalStorageValue(this.USER_EMAIL),
      desgnation: this.getLocalStorageValue(this.USER_POSITION),
      registration: this.getLocalStorageValue(this.USER_REGISTRATION)
    }
  }

}
