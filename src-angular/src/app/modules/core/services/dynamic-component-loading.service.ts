import {Injectable, Type, ViewContainerRef} from '@angular/core';
import {Observable, Subject} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class DynamicComponentLoadingService {
  private investmentComponentTrigger: Subject<any> = new Subject<any>();

  constructor() {
  }

  triggerInvestmentComponentLoading(data: any) {
    this.investmentComponentTrigger.next(data);
  }

  getInvestmentComponentTrigger(): Observable<any> {
    return this.investmentComponentTrigger.asObservable();
  }

  loadComponent<T>(container: ViewContainerRef, component: Type<T>, inputData?: any): void {
    console.log(`DynamicComponentLoadingService loadComponent start`);
    container.clear();
    const componentRef = container.createComponent(component);
    if (inputData && componentRef.instance) {
      Object.assign(componentRef.instance, inputData);
    }
    console.log(`DynamicComponentLoadingService loadComponent stop`);
  }


}
