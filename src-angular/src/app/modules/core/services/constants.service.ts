import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConstantsService {
  readonly API_SYSTEM_CONFIG = 'https://localhost:8081/api/system/config/properties';
  private readonly API_BASE_URL = 'https://localhost:8081/api';
  readonly API_CITIES_ENDPOINT = `${this.API_BASE_URL}/cities`;
  readonly API_DEVELOPER_BY_SYSTEM_CODE_ENDPOINT = `${this.API_BASE_URL}/developers/code`;
  readonly API_INVESTMENTS_ENDPOINT = `${this.API_BASE_URL}/investments`;

  getApiPremisesByInvestmentEndpoint(investmentId: number): string {
    return `${this.API_BASE_URL}/premises/investment/${investmentId}`;
  }
  getApiPremiseMinMaxTotalPriceByInvestmentId(investmentId: number): string {
    return `${this.API_BASE_URL}/premises/investment/${investmentId}/enhancedPremiseData`;
  }
  getApiPremiseByIdEndpoint(premiseId: string): string {
    return `${this.API_BASE_URL}/premises/${premiseId}`;
  }
  getApiInvestmentByPremiseIdEndpoint(premiseId: string): string {
    return `${this.API_BASE_URL}/investmentByPremiseId/${premiseId}`;
  }
  getApiTranslationEndpoint(entityId: number, domain: string, key: string): string {
    return `${this.API_BASE_URL}/system/${entityId}/${domain}/${key}`;
  }



}
