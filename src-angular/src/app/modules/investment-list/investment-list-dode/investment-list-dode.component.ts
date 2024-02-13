import {Component, OnDestroy, OnInit} from '@angular/core';
import {InvestmentResponse} from "../../core/models/investment.model";
import {CityResponse} from "../../core/models/city.model";
import {Subject, Subscription, takeUntil} from "rxjs";
import {InvestmentsService} from "../../core/services/investments.service";
import {CitiesService} from "../../core/services/cities.service";
import {DynamicComponentLoadingService} from "../../core/services/dynamic-component-loading.service";


@Component({
  selector: 'app-investment-list-dode',
  templateUrl: './investment-list-dode.component.html',
  styleUrls: ['./investment-list-dode.component.scss']
})
export class InvestmentListDodeComponent implements OnInit, OnDestroy {
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
  }

  private subscribeToCityChanges() {
    this.dynamicLoadingService.getInvestmentComponentTrigger()
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(data => {
        console.log('City change triggered:', data); // Add this line
        console.log(`Are cities loaded? ${this.citiesLoaded}`); // Add this line
        if (this.citiesLoaded && data.cityName !== this.cityName) {
          console.log(`Changing city to: ${data.cityName}`); // Add this line
          this.cityName = data.cityName;
          this.getInvestments();
        }
      });
  }

  private getInvestments() {
    console.log('getInvestments called'); // Log when the method is called
    this.investmentsService.getInvestments()
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: data => {
          console.log('Investments fetched:', data.investments); // Add this line
          this.allInvestments = data.investments;
          this.filterInvestmentsByCity(this.cityName);
        },
        error: error => console.error('Error fetching investments:', error)
      });
  }

  private filterInvestmentsByCity(cityName: string): void {
    console.log('filterInvestmentsByCity called with:', cityName); // Log when the function is called
    if (!cityName) {
      this.investments = [...this.allInvestments];
      console.log('No city name provided, showing all investments.'); // Add this line
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
    console.log('Filtered investments for city:', cityName, this.investments); // Log the filtered investments
    console.log('Filtered investments for city length:', cityName, this.investments.length); // Log the number of filtered investments
  }

  ngOnDestroy(): void {
    if (this.subscriptionToCityChanges) {
      this.subscriptionToCityChanges.unsubscribe();
    }
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

}
