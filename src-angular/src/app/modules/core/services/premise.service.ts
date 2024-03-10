import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SearchResultPremiseModel} from "../models/searchResultPremise.model";
import {ConstantsService} from "./constants.service";
import {EnhancedPremiseModel} from "../models/enhancedPremise.model";

@Injectable({
  providedIn: 'root'
})
export class PremiseService {

  constructor(private http: HttpClient, private constanceService: ConstantsService) {
  }

  getPremiseById(id: string) {
    return this.http.get<SearchResultPremiseModel>(this.constanceService.getApiPremiseByIdEndpoint(id));
  }

  getPremisesByInvestmentId(id: number) {
    return this.http.get<SearchResultPremiseModel>(this.constanceService.getApiPremisesByInvestmentEndpoint(id));
  }

  getPremiseMinMaxTotalPriceByInvestmentId(id: number) {
    return this.http.get<EnhancedPremiseModel>(this.constanceService.getApiPremiseMinMaxTotalPriceByInvestmentId(id));
  }

}
