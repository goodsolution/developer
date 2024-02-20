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
  cityId = '';
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
      const id: string | null = params.get('id');
      console.log('id', id);
      this.handleCityNameChange(id);
    });
    console.log('InvestmentListComponent ngOnInit stop');
  }

  private handleCityNameChange(newCityId: string | null) {
    if (newCityId && newCityId !== this.cityId) {
      console.log(`City id changed to: ${newCityId}`);
      this.cityId = newCityId;
      // Trigger the dynamic component loading with the new city name
      this.dynamicLoadingService.triggerInvestmentComponentLoading({cityId: this.cityId});
    }
  }

  ngOnDestroy(): void {
    if (this.routeSub) {
      this.routeSub.unsubscribe();
    }
  }

}
