import {Component, OnDestroy, OnInit} from '@angular/core';
import {InvestmentResponse} from "../../core/models/investment.model";
import {CityResponse} from "../../core/models/city.model";
import {Subject, Subscription, takeUntil} from "rxjs";
import {InvestmentsService} from "../../core/services/investments.service";
import {ActivatedRoute} from "@angular/router";
import {CitiesService} from "../../core/services/cities.service";
import {DynamicComponentLoadingService} from "../../core/services/dynamic-component-loading.service";

@Component({
  selector: 'app-investment-list-antal',
  templateUrl: './investment-list-antal.component.html',
  styleUrls: ['./investment-list-antal.component.scss']
})
export class InvestmentListAntalComponent implements OnInit, OnDestroy {
  allInvestments: InvestmentResponse[] = [];
  investments: InvestmentResponse[] = [];
  cities: CityResponse[] = [];
  cityName: string = '';
  private unsubscribe$ = new Subject<void>();
  private subscriptionToCityChanges?: Subscription;
  private citiesLoaded = false;

  constructor(
    private dynamicLoadingService: DynamicComponentLoadingService,
    private investmentsService: InvestmentsService,
    private cityService: CitiesService,
  ) {
  }

  ngOnInit(): void {
    this.loadCities();
  }

  private loadCities() {
    console.log('InvestmentListDodeComponent loadCities start');
    if (this.citiesLoaded) {
      return;
    }
    this.cityService.getCities()
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: data => {
          console.log('Cities loaded:', data.cities);
          this.cities = data.cities;
          this.citiesLoaded = true;
          if (!this.subscriptionToCityChanges) {
            this.subscribeToCityChanges();
          }
        },
        error: error => {
          console.error('Error fetching cities:', error);
          this.citiesLoaded = false;
        }
      });
    console.log('InvestmentListDodeComponent loadCities stop');
  }

  private subscribeToCityChanges() {
    this.dynamicLoadingService.getInvestmentComponentTrigger()
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(data => {
        if (this.citiesLoaded && data.cityName !== this.cityName) {
          this.cityName = data.cityName;
          this.getInvestments();
        }
      });
  }

  private getInvestments() {
    this.investmentsService.getInvestments()
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: data => {
          this.allInvestments = data.investments;
          this.filterInvestmentsByCity(this.cityName);
        },
        error: error => console.error('Error fetching investments:', error)
      });
  }

  private filterInvestmentsByCity(cityName: string): void {
    if (!cityName) {
      this.investments = [...this.allInvestments];
      return;
    }
    this.investments = this.allInvestments.filter(investment => {
      const city = this.cities
        .find(c => c.name.toLowerCase() === cityName.toLowerCase());
      if (!city) {
        console.warn(`City not found: ${cityName}`);
      }
      return city && investment.cityId === city.id;
    });
  }

  ngOnDestroy(): void {
    if (this.subscriptionToCityChanges) {
      this.subscriptionToCityChanges.unsubscribe();
    }
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

}
