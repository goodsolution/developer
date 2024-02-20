import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConstantsService {
  readonly API_SYSTEM_CONFIG = 'https://localhost:8081/api/system/config/properties';
  readonly API_CITIES_ENDPOINT = 'https://localhost:8081/api/cities';
  readonly API_DEVELOPER_BY_SYSTEM_CODE_ENDPOINT = 'https://localhost:8081/api/system/developers/code';
  readonly API_INVESTMENTS_ENDPOINT = 'https://localhost:8081/api/investments';


}
