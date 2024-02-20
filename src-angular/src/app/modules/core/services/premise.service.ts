import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SearchResultPremiseModel} from "../models/searchResultPremise.model";
import {ConstantsService} from "./constants.service";

@Injectable({
  providedIn: 'root'
})
export class PremiseService {
  private apiUrl = this.constanceService.API_PREMISES_BY_INVESTMENT_ID_ENDPOINT;

  constructor(private http: HttpClient, private constanceService: ConstantsService) {
  }

  getPremisesByInvestmentId(id: number) {
    return this.http.get<SearchResultPremiseModel>(`${this.apiUrl}/${id}`);
  }

}
