import { Injectable } from '@angular/core';
import {Observable, Subject} from "rxjs";
import {InvestmentComponentData} from "../models/InvestmentComponentData .model";

@Injectable({
  providedIn: 'root'
})
export class DynamicComponentLoadingServiceService {
  private investmentComponentTrigger: Subject<InvestmentComponentData> = new Subject<InvestmentComponentData>();

  constructor() { }

  triggerInvestmentComponentLoading(data: InvestmentComponentData) {
    this.investmentComponentTrigger.next(data);
  }

  getInvestmentComponentTrigger(): Observable<InvestmentComponentData> {
    return this.investmentComponentTrigger.asObservable();
  }

}
