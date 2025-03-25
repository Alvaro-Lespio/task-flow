import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient:HttpClient,private router: Router) { }

  private LOGIN_URL = 'http://localhost:8080/api/users/login';
  private tokenKey = 'authToken';

  login(user: string, password: string): Observable<string> {
    return this.httpClient.post(this.LOGIN_URL, { username: user, password }, { responseType: 'text' }).pipe(
        tap(token => {
            if (token) {
                this.setToken(token);
            }
        })
    );
}

  private setToken(token:string): void{
    localStorage.setItem(this.tokenKey,token);
  }

  private getToken():string | null{
    return localStorage.getItem(this.tokenKey);
  }

  isAuthenticated():boolean{
    const token = this.getToken();
    if(!token){
      return false;
    }
    const payload = JSON.parse(atob(token.split('.')[1]));
    const exp = payload.exp * 1000;

    return Date.now() < exp;
  }

  logout():void{
    localStorage.removeItem(this.tokenKey);
    this.router.navigate(['/login'])
  }
}
