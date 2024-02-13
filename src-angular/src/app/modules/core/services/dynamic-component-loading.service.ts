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
    console.log('triggerInvestmentComponentLoading');
    console.log('data', data);
    this.investmentComponentTrigger.next(data);
  }

  getInvestmentComponentTrigger(): Observable<any> {
    console.log('getInvestmentComponentTrigger');
    return this.investmentComponentTrigger.asObservable();
  }

  loadComponent<T>(container: ViewContainerRef, component: Type<T>, inputData?: any): void {
    console.log(`Loading component of type: ${component.name}`);

    // Clear the container and log the action
    console.log('Clearing container before creating the component');
    container.clear();

    // Create the component and log the action
    const componentRef = container.createComponent(component);
    console.log(`Component ${component.name} created`, componentRef);

    // If inputData is provided, assign it to the component's instance and log the inputData
    if (inputData && componentRef.instance) {
      console.log(`Assigning inputData to component ${component.name}:`, inputData);
      Object.assign(componentRef.instance, inputData);
    }

    // Log any additional setup done for the component
    console.log(`Finished loading component of type: ${component.name}`);
  }


}
