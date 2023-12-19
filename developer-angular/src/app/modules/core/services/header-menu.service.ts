import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../environments/environment.development";
import {Observable} from "rxjs";
import {HeaderMenuResponse} from "../../models/header-menu.model";

@Injectable({
  providedIn: 'root'
})
export class HeaderMenuService {

  apiUrl = environment.frontMenuDeveloperEndpoint;
  constructor(private http: HttpClient) { }

  getMenu():Observable<HeaderMenuResponse[]>{
    return this.http.get<HeaderMenuResponse[]>(`${this.apiUrl}`)
  }


}
