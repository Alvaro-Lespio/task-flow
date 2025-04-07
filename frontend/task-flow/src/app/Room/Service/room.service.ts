import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ChangeDetectorRef, Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { AuthService } from '../../authentication/services/auth.service';
import { Room } from '../Entitie/room';
import { JoinRoom } from '../Entitie/JoinRoom';

@Injectable({
  providedIn: 'root'
})
export class RoomService {
  private API_URL = 'http://localhost:8080/api/room'
  constructor(private http: HttpClient,private authService:AuthService) { 
  }

  joinRoom(roomCode:string, password:string):Observable<any>{
    const body = new JoinRoom(roomCode,password);
    const headers = new HttpHeaders({
      'Authorization' : `Bearer ${this.authService.getToken()}`
    });
    return this.http.post(`${this.API_URL}/join/${roomCode}/${password}`,body,{headers})
  }

  createRoom(name:string, password: string):Observable<any>{
    const body = new Room(name,password);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.getToken()}`
    });
    return this.http.post(this.API_URL,body,{headers});
  }
  getRooms():Observable<any[]>{
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.getToken()}`
    });
    return this.http.get<any[]>(this.API_URL,{headers});
  }
  getRoomById(id: string | null): Observable<any> {
    if (!this.authService.isLoggedIn()) {
      return throwError(() => new Error('User not authenticated'));
    }

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.getToken()}`,
      'Content-Type': 'application/json'
    });

    return this.http.get<any>(`${this.API_URL}/${id}`, { headers }).pipe(
      catchError(this.handleError)
    );
  }  
  
  createTask(roomId: string, task: {
    title: string;
    description: string;
    finishDate: string;
    difficulty: string; // HIGH, MEDIUM, LOW will be passed here
    assignedTo?: string;
  }): Observable<any> {
    if (!this.authService.isLoggedIn()) {
      return throwError(() => new Error('User not authenticated'));
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.getToken()}`,
      'Content-Type': 'application/json'
    });
    return this.http.post(`${this.API_URL}/task/${roomId}`, task, { headers }).pipe(
      catchError(this.handleError)
    );
  }

  deleteTask(roomId: string, taskId: number): Observable<any> {
    if (!this.authService.isLoggedIn()) {
      return throwError(() => new Error('User not authenticated'));
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.getToken()}`
    });
    return this.http.delete(`${this.API_URL}/task/${roomId}/${taskId}`, { headers, responseType: 'text' }).pipe(
      catchError(this.handleError)
    );
  }

  updateTask(roomId: string, taskId: number, task: {
    title: string;
    description: string;
    finishDate: string;
    difficulty: string;
    assignedTo?: string;
  }): Observable<any> {
    if (!this.authService.isLoggedIn()) {
      return throwError(() => new Error('User not authenticated'));
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.getToken()}`,
      'Content-Type': 'application/json'
    });
    return this.http.put(`${this.API_URL}/task/${roomId}/${taskId}`, task, { headers }).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: any): Observable<never> {
    let errorMessage = 'An error occurred';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(() => new Error(errorMessage));
  }
}
