import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment.development";
import {HttpClient} from "@angular/common/http";
import {Observable, of, tap} from "rxjs";
import {SearchResultCityModel} from "../models/searchResultCity.model";
import {CityResponse} from "../models/city.model";

@Injectable({
  providedIn: 'root'
})
export class CitiesService {
  private apiUrl = environment.citiesEndpoint;
  private cache!: SearchResultCityModel;

  constructor(private http: HttpClient) {
  }

  getCities(): Observable<SearchResultCityModel> {
    if (this.cache) {
      console.log('Fetching data from cache Cities');
      return of(this.cache); // Return cached data
    } else {
      console.log('Fetching data from API Cities');
      return this.http.get<SearchResultCityModel>(`${this.apiUrl}`)
        .pipe(tap(data => this.cache = data));
    }
  }

}
