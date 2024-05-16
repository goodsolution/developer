import { Injectable } from '@angular/core';
import {jwtDecode} from "jwt-decode";


@Injectable({
  providedIn: 'root'
})
export class TokenService {
  private readonly TOKEN_KEY = 'auth_token';
  private readonly ROLES_KEY = 'auth_roles';

  constructor() { }

  saveToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
    const roles = this.extractRolesFromToken(token);
    this.saveRoles(roles);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
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
    this.clearRoles();
  }

  public saveRoles(roles: string[]): void {
    localStorage.setItem(this.ROLES_KEY, JSON.stringify(roles));
  }

  getRoles(): string[] {
    const roles = localStorage.getItem(this.ROLES_KEY);
    return roles ? JSON.parse(roles) : [];
  }

  public clearRoles(): void {
    localStorage.removeItem(this.ROLES_KEY);
  }

  private extractRolesFromToken(token: string): string[] {
    try {
      const decoded: any = jwtDecode(token);
      return decoded.roles || [];
    } catch (error) {
      console.error('Failed to decode token and extract roles', error);
      return [];
    }
  }
}
