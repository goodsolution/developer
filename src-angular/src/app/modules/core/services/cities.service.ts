import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment.development";
import {HttpClient} from "@angular/common/http";
import {interval, Observable, of, switchMap, tap} from "rxjs";
import {SearchResultCityModel} from "../models/searchResultCity.model";

@Injectable({
  providedIn: 'root'
})
export class CitiesService {
  private apiUrl = environment.citiesEndpoint;
  private cache!: SearchResultCityModel;
  private lastUpdated: number = 0;
  private updateInterval = 60000; // 15 sec in milliseconds

  constructor(private http: HttpClient) {
    this.startPeriodicUpdate()
  }

  private startPeriodicUpdate(): void {
    console.log('Starting periodic update Cities 60 sec interval');
    interval(this.updateInterval).pipe(
      switchMap(() => {
        return this.fetchDataFromAPI();
      })
    ).subscribe();
  }

  getCities(): Observable<SearchResultCityModel> {
    // if (this.cache && (new Date().getTime() - this.lastUpdated < this.updateInterval)) {
    //   console.log(this.lastUpdated);
    //   console.log(this.updateInterval);
    //   console.log(new Date().getTime());
    //   console.log(new Date().getTime() - this.lastUpdated);
    //   console.log('Fetching data from cache Cities');
    //   return of(this.cache); // Return cached data
    // } else {
    //   console.log('Fetching data from API Cities');
    //   return this.fetchDataFromAPI();
    // }
    if(this.cache){
      console.log('Fetching data from cache Cities');
      return of(this.cache);
    } else {
      console.log('Fetching data from API Cities');
      return this.fetchDataFromAPI();
    }
  }

  private fetchDataFromAPI(): Observable<SearchResultCityModel> {
    return this.http.get<SearchResultCityModel>(`${this.apiUrl}`).pipe(
      tap(data => {
        if (this.isDataUpdated(data)) {
          this.cache = data;
          this.lastUpdated = new Date().getTime();
        } else {
          console.log('cities- No changes in data, cache not updated');
        }
      })
    );
  }

  private isDataUpdated(newData: SearchResultCityModel): boolean {
    if (!this.cache || this.cache.cities.length !== newData.cities.length) {
      console.log('CIty is DataUpdated Cache is empty or data length is different')
      return true;
    }
    // Create sets of IDs for easy comparison
    const cachedIds = new Set(this.cache.cities.map(investment => investment.id));
    const newIds = new Set(newData.cities.map(investment => investment.id));
    // Check if every ID in the new data exists in the cache
    for (const id of newIds) {
      if (!cachedIds.has(id)) {
        return true;
      }
    }
    // Check if every ID in the cache exists in the new data
    for (const id of cachedIds) {
      if (!newIds.has(id)) {
        return true;
      }
    }
    // If no changes are found
    return false;
  }


}
