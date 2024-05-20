import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthenticationService} from "../../../core/services/authentication.service";
import {Router} from "@angular/router";
import {Subject, takeUntil} from "rxjs";
import {TranslateService} from "@ngx-translate/core";
import * as CryptoJS from 'crypto-js';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {
  private unsubscribe$ = new Subject<void>();
  username: string = '';
  password: string = '';
  hide: boolean = true;
  errorMessage: string = '';

  constructor(
    private authService: AuthenticationService,
    private router: Router,
    private translate: TranslateService
  ) {
  }

  ngOnInit(): void {
    console.log('LoginComponent initialized');
    this.authService.isLoggedIn().pipe(
      takeUntil(this.unsubscribe$)
    ).subscribe((loggedIn: boolean) => {
      if (loggedIn) {
        this.router.navigate(['/dashboard']);
      }
    });
  }

  onLogin(): void {
    this.errorMessage = ''; // Clear previous error messages
    console.log('Attempting login with', this.username);

    const encryptedPassword = btoa(this.password.trim());
    console.log('Encrypted Password:', encryptedPassword);

    this.authService.login(this.username, encryptedPassword).pipe(
      takeUntil(this.unsubscribe$)
    ).subscribe({
      next: (token) => {
        console.log('Login successful, navigating to dashboard');
        this.router.navigate(['/dashboard']);
      },
      error: (error: any) => {
        console.error('Login error:', error);
        console.log('Full error object:', JSON.stringify(error));

        const errorStatus = error?.status;
        const errorResponse = error?.error;
        const errorMessage = error?.message;

        console.error('Error status:', errorStatus);
        console.error('Error response:', errorResponse);
        console.error('Error message:', errorMessage);

        if (errorStatus === 401) {
          console.log('Setting specific 401 error message');
          this.setError('shared.error.login.failed');
        } else {
          console.log('Setting unknown error message');
          this.setError('shared.error.unknown');
        }
      }
    });
  }

  setError(key: string): void {
    this.translate.get(key).subscribe((res: string) => {
      this.errorMessage = res;
      console.log('Error message set:', res);
    });
  }

  ngOnDestroy(): void {
    console.log('LoginComponent destroyed');
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

}
