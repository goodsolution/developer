import { Injectable } from '@angular/core';
import {environment} from "../../../../environments/environment.development";
import {HttpClient} from "@angular/common/http";
import {SearchResultPremiseModel} from "../models/searchResultPremise.model";

@Injectable({
  providedIn: 'root'
})
export class PremiseService {
  private apiUrl = environment.premisesByInvestmentIdEndpoint;

  constructor(private http: HttpClient) { }

  getPremisesByInvestmentId(id: number) {
    return this.http.get<SearchResultPremiseModel>(`${this.apiUrl}/${id}`);
  }

}
