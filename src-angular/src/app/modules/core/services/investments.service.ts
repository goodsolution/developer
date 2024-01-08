import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment.development";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {SearchResultInvestmentModel} from "../models/searchResultInvestment.model";

@Injectable({
  providedIn: 'root'
})
export class InvestmentsService {
  apiUrl = environment.investmentsEndpoint;

  constructor(private http: HttpClient) {
  }

  getInvestments(): Observable<SearchResultInvestmentModel> {
    return this.http.get<SearchResultInvestmentModel>(`${this.apiUrl}`);
  }

}


