import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment.development";
import {HttpClient} from "@angular/common/http";
import {catchError, Observable, of, shareReplay, tap, throwError} from "rxjs";
import {SearchResultInvestmentModel} from "../models/searchResultInvestment.model";

@Injectable({
  providedIn: 'root'
})
export class InvestmentsService {

  private apiUrl = environment.investmentsEndpoint;
  private investmentCache$: Observable<SearchResultInvestmentModel> | null = null;
  private cacheExpirationMs = 100000; // Cache expiration in milliseconds (e.g., 300000ms = 5 minutes)
  private lastRequestTime: number | null = null;

  constructor(private http: HttpClient) { }

  getInvestments(): Observable<SearchResultInvestmentModel> {
    const currentTime = new Date().getTime();

    if (this.investmentCache$ && this.lastRequestTime && (currentTime - this.lastRequestTime < this.cacheExpirationMs)) {
      // console.log('Fetching data from cache Investments');
      return this.investmentCache$;
    } else {
      // console.log('Fetching data from API Investments');
      this.investmentCache$ = this.http.get<SearchResultInvestmentModel>(this.apiUrl).pipe(
        tap(() => this.lastRequestTime = new Date().getTime()),
        shareReplay(1),
        catchError(error => {
          // console.error('Error fetching investments data:', error);
          return throwError(() => error);
        })
      );
      return this.investmentCache$;
    }
  }

}


