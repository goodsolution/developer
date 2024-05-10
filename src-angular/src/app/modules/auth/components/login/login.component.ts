import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../../core/services/authentication.service";
import {Router} from "@angular/router";
import {TokenService} from "../../../core/services/token.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit{
  username: string = '';
  password: string = '';
  hide = true; // For toggling password visibility

  constructor(
    private authService: AuthenticationService,
    private tokenService: TokenService,
    private router: Router
  ) {}

  ngOnInit() {
    // Check if the user is already logged in
    if (this.tokenService.getToken()) {
      this.router.navigate(['/dashboard']); // Redirect to dashboard if token exists
    }
  }

  onLogin(): void {
    this.authService.login(this.username, this.password).subscribe({
      next: token => {
        console.log('Login successful, token:', token);
        // Redirect or further actions after successful login
      },
      error: error => {
        console.error('Login failed:', error);
        // Display error message to user or handle it otherwise
      }
    });
  }

}
