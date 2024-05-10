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
    return this.http.post<LoginResponse>(this.apiUrl, { username, password }, {
      headers: {'Content-Type': 'application/json'},
      observe: 'response'
    })
      .pipe(
        map(response => {
          const token = response.body?.token;
          if (token) {
            localStorage.setItem('currentUser', JSON.stringify({ username, token }));
            return token;
          } else {
            // If no token in the response, throw an error to be caught by catchError
            throw new Error('No token received');
          }
        }),
        catchError((error: HttpErrorResponse) => {
          // Optionally, handle the error and log or display to the user
          console.error('Login error:', error.message);
          return throwError(() => new Error('Login failed, please try again.'));
        })
      );
  }

}
