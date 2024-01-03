import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment.development";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {SearchResultCityModel} from "../models/searchResultCity.model";

@Injectable({
  providedIn: 'root'
})
export class CitiesService {
  apiUrl = environment.citiesEndpoint;

  constructor(private http: HttpClient) {
  }

  getCities(): Observable<SearchResultCityModel> {
    return this.http.get<SearchResultCityModel>(`${this.apiUrl}`);
  }

}
