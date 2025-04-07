import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient:HttpClient,private router: Router) { }

  private API_URL = 'http://localhost:8080/api/users';
  private tokenKey = 'authToken';

  login(user: string, password: string): Observable<string> {
    return this.httpClient.post(`${this.API_URL}/login`, { username: user, password }, { responseType: 'text' }).pipe(
        tap(token => {
            if (token) {
                this.setToken(token);
            }
        })
    );   
}
register(user: { username: string; password: string }): Observable<any> {
  return this.httpClient.post(`${this.API_URL}/register`, user);
}

  private setToken(token:string): void{
    localStorage.setItem(this.tokenKey,token);
  }

  public getToken():string | null{
    if(typeof window != undefined){
      return localStorage.getItem(this.tokenKey);
    }else{
      return null;
    }
    
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

  isLoggedIn(): boolean {
    return !!this.tokenKey;
  }
}
