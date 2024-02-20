import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class DynamicComponentLoadingService {
  private investmentComponentTrigger: Subject<any> = new Subject<any>();

  triggerInvestmentComponentLoading(data: any) {
    this.investmentComponentTrigger.next(data);
  }

  getInvestmentComponentTrigger(): Observable<any> {
    return this.investmentComponentTrigger.asObservable();
  }

}
