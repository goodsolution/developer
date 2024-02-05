import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment.development";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {HeaderLogoUrlData} from "../models/header-logo-url.model";

@Injectable({
  providedIn: 'root'
})
export class HeaderLogoUrlService {

  apiUrl = environment.logoDeveloperEndpoint;

  constructor(private http: HttpClient) {
  }

  getLogoUrl(): Observable<HeaderLogoUrlData> {
    return this.http.get<HeaderLogoUrlData>(`${this.apiUrl}`);
  }


}
