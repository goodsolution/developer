import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {BehaviorSubject, catchError, map, Observable, throwError} from "rxjs";
import {TokenService} from "./token.service";
import {Router} from "@angular/router";
import {ConstantsService} from "./constants.service";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private loggedIn = new BehaviorSubject<boolean>(this.hasToken());

  constructor(
    private http: HttpClient,
    private tokenService: TokenService,
    private router: Router,
    private constantsService: ConstantsService
  ) {}

  login(username: string, password: string): Observable<string> {
    return this.http.post<{ token: string }>(this.constantsService.getApiLoginEndpoint(), { login: username, passwordHash: password }, {
      headers: { 'Content-Type': 'application/json' }
    }).pipe(
      map(response => {
        const token = response.token;
        if (token) {
          this.tokenService.saveToken(token);
          this.loggedIn.next(true);
          return token;
        } else {
          throw new Error('Authentication failed: No token received');
        }
      }),
      catchError((error: HttpErrorResponse) => {
        console.error('AuthService error:', error);
        const errorMessage = error.error.message || 'An error occurred during login';
        return throwError(() => new Error(errorMessage));
      })
    );
  }

  logout(): void {
    this.tokenService.clearToken();
    this.loggedIn.next(false);
    this.router.navigate(['/login']);
  }

  isLoggedIn(): Observable<boolean> {
    return this.loggedIn.asObservable();
  }

  isLoggedInSync(): boolean {
    return !!this.tokenService.getToken() && !this.tokenService.isTokenExpired(this.tokenService.getToken()!);
  }

  private hasToken(): boolean {
    return !!this.tokenService.getToken();
  }

}
