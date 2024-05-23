import {Injectable} from '@angular/core';
import {jwtDecode} from "jwt-decode";


@Injectable({
  providedIn: 'root'
})
export class TokenService {
  private readonly TOKEN_KEY = 'auth_token';

  saveToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  getRolesFromToken(token: string): string[] {
    try {
      const decoded: any = jwtDecode(token);
      return decoded.roles || [];
    } catch (error) {
      console.error('Failed to decode token and extract roles', error);
      return [];
    }
  }

  isTokenExpired(token: string): boolean {
    try {
      const decoded: any = jwtDecode(token);
      const now = Date.now() / 1000; // Current time in seconds since epoch
      console.log('Token expiration:', decoded.exp, 'Current time:', now);
      return decoded.exp < now;
    } catch (error) {
      console.error('Failed to decode token', error);
      return true;
    }
  }

  clearToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }

  getUsernameFromToken(token: string): string | null {
    try {
      const decoded: any = jwtDecode(token);
      return decoded.sub || null; // 'sub' is the subject, usually the username
    } catch (error) {
      console.error('Failed to decode token and extract username', error);
      return null;
    }
  }

}
