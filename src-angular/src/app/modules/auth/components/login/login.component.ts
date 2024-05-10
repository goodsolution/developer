import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../../core/services/authentication.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  username!: string;
  password!: string;
  hide = true; // For toggling password visibility

  constructor(private authService: AuthenticationService) {}

  ngOnInit() {}

  onLogin(): void {
    this.authService.login(this.username, this.password).subscribe({
      next: token => {
        console.log('Login successful, token:', token);
      },
      error: error => {
        console.error('Login failed:', error);
      }
    });
  }

}
