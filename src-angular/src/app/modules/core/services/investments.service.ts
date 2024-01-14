import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment.development";
import {HttpClient} from "@angular/common/http";
import {Observable, of, tap} from "rxjs";
import {SearchResultInvestmentModel} from "../models/searchResultInvestment.model";

@Injectable({
  providedIn: 'root'
})
export class InvestmentsService {
  apiUrl = environment.investmentsEndpoint;
  private investmentsCache: SearchResultInvestmentModel | null = null;

  constructor(private http: HttpClient) { }

  getInvestments(): Observable<SearchResultInvestmentModel> {
    if (this.investmentsCache) {
      console.log('Fetching data from cache Investments');
      return of(this.investmentsCache); // Return cached data
    } else {
      console.log('Fetching data from API Investments');
      return this.http.get<SearchResultInvestmentModel>(`${this.apiUrl}`)
        .pipe(tap(data => this.investmentsCache = data)); // Cache data
    }
  }
  //TODO cache mechanizm angular
  //TODO once hour check - shot to DB

}


