import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {DynamicComponentLoadingService} from "../core/services/dynamic-component-loading.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-investment-list',
  templateUrl: './investment-list.component.html',
  styleUrls: ['./investment-list.component.scss']
})
export class InvestmentListComponent implements OnInit, OnDestroy {
  cityName: string = '';
  private routeSub!: Subscription;

  constructor(
    private dynamicLoadingService: DynamicComponentLoadingService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    console.log('InvestmentListComponent ngOnInit start');
    // Subscribe to paramMap changes instead of the initial value.
    this.routeSub = this.route.paramMap.subscribe(params => {
      const name = params.get('name');
      console.log('name', name);
      this.handleCityNameChange(name);
    });
    console.log('InvestmentListComponent ngOnInit stop');
  }

  private handleCityNameChange(newCityName: string | null) {
    if (newCityName && newCityName !== this.cityName) {
      console.log(`City name changed to: ${newCityName}`);
      this.cityName = newCityName;
      // Trigger the dynamic component loading with the new city name
      this.dynamicLoadingService.triggerInvestmentComponentLoading({cityName: this.cityName});
    }
  }

  ngOnDestroy(): void {
    if (this.routeSub) {
      this.routeSub.unsubscribe();
    }
  }

}
