import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConstantsService {
  readonly API_SYSTEM_CONFIG = 'https://localhost:8081/api/system/config/properties';
  private readonly API_BASE_URL = 'https://localhost:8081/api';
  readonly API_CITIES_BY_DEVELOPER_ENDPOINT = `${this.API_BASE_URL}/cities_by_developer`;
  readonly API_CITIES_ENDPOINT = `${this.API_BASE_URL}/cities`;
  readonly API_DEVELOPER_BY_SYSTEM_CODE_ENDPOINT = `${this.API_BASE_URL}/developers/code`;
  readonly API_INVESTMENTS_ENDPOINT = `${this.API_BASE_URL}/investments`;
  readonly API_LOGIN_ENDPOINT = `${this.API_BASE_URL}/auth/login`;
  readonly ERROR_MESSAGE = 'An error has occurred';
  readonly API_ENCRYPTION_KEY = `${this.API_BASE_URL}/auth/encryption`;
  readonly attributes = {
    SALES_STATUS: 'salesStatus',
    TECHNICAL_STATUS: 'technicalStatus',
    EXPOSURE: 'exposure',
    INVESTMENT: 'investment',
    DESCRIPTION: 'description',
  }

  getApiSoftDeleteDeveloperEndpoint(developerId: number): string {
    return `${this.API_BASE_URL}/developers/${developerId}`;
  }
  getApiRegisterDeveloperEndpoint(): string {
    return `${this.API_BASE_URL}/developers/register`;
  }
  getApiUpdateDeveloperEndpoint(developerId: number): string {
    return `${this.API_BASE_URL}/developers/${developerId}`;
  }
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
  getApiDictionaryEndpoint(domain: string, key: string): string {
    return `${this.API_BASE_URL}/system/dictionary/${domain}/${key}`;
  }
  getApiLoginEndpoint(): string {
    return this.API_LOGIN_ENDPOINT;
  }
  getApiEncryptionKeyEndpoint(): string {
    return this.API_ENCRYPTION_KEY;
  }
  getApiAllActiveDevelopersEndpoint(): string {
    return `${this.API_BASE_URL}/developers`;
  }

}
