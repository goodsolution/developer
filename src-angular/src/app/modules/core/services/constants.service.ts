import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConstantsService {
  readonly API_SYSTEM_CODE = 'https://localhost:8081/api/system/config/properties';

  constructor() { }
}
