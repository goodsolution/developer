import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {catchError, map, Observable, throwError} from "rxjs";
import {LoginResponse} from "../models/loginResponse.model";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private apiUrl = 'https://localhost:8081/api/auth/login';

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<string> {
    return this.http.post<any>(this.apiUrl, { login: username, passwordHash: password }, {
      headers: { 'Content-Type': 'application/json' }
    }).pipe(
      map(response => {
        const token = response.token;
        if (!token) {
          throw new Error('Authentication failed.');
        }
        return token;
      }),
      catchError(error => {
        console.error('Authentication error:', error);
        return throwError(() => new Error('Authentication failed.'));
      })
    );
  }

}
