import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SearchResultPremiseModel} from "../models/searchResultPremise.model";
import {ConstantsService} from "./constants.service";

@Injectable({
  providedIn: 'root'
})
export class PremiseService {

  constructor(private http: HttpClient, private constanceService: ConstantsService) {
  }

  getPremisesByInvestmentId(id: number) {
    return this.http.get<SearchResultPremiseModel>(this.constanceService.getApiPremisesByInvestmentEndpoint(id));
  }

  getPremiseByInvestmentIdAndMinTotalPrice(id: number) {
    return this.http.get<SearchResultPremiseModel>(this.constanceService.getApiPremisesMinPriceByInvestmentIdEndpoint(id));
  }

  getPremiseByInvestmentIdAndMaxTotalPrice(id: number) {
    return this.http.get<SearchResultPremiseModel>(this.constanceService.getApiPremisesMaxPriceByInvestmentIdEndpoint(id));
  }

}
