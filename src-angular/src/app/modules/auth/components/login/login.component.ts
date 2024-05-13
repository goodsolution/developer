import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthenticationService} from "../../../core/services/authentication.service";
import {Router} from "@angular/router";
import {Subject, takeUntil} from "rxjs";

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

  constructor(
    private authService: AuthenticationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.authService.isLoggedIn().pipe(
      takeUntil(this.unsubscribe$)
    ).subscribe((loggedIn: boolean) => {
      if (loggedIn) {
        this.router.navigate(['/dashboard']);
      }
    });
  }

  onLogin(): void {
    this.authService.login(this.username, this.password).subscribe({
      next: (token) => {
        this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        console.error('Login error:', error);
      }
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

}
