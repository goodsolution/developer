import { Component, OnInit, OnDestroy } from '@angular/core';
import { InvestmentResponse } from "../../core/models/investment.model";
import { CityResponse } from "../../core/models/city.model";
import { Subject, takeUntil } from "rxjs";
import { InvestmentsService } from "../../core/services/investments.service";
import { CitiesService } from "../../core/services/cities.service";
import { DynamicComponentLoadingServiceService } from "../../core/services/dynamic-component-loading-service.service";

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

  constructor(
    private investmentsService: InvestmentsService,
    private cityService: CitiesService,
    private dynamicLoadingService: DynamicComponentLoadingServiceService // Inject the service for dynamic component loading
  ) {}

  ngOnInit(): void {
    // Subscribe to the dynamic loading service to get cityName
    this.dynamicLoadingService.getInvestmentComponentTrigger().pipe(
      takeUntil(this.unsubscribe$)
    ).subscribe(data => {
      this.cityName = data.cityName;
      console.log('City name received in dynamically loaded component:', this.cityName);
      this.loadComponentData(); // Call a method to load investments and cities based on cityName
    });
    this.getCities();
  }

  loadComponentData() {
    // Assuming getCities does not depend on cityName and can be called independently
    this.getInvestments(); // Now getInvestments should be aware of the updated cityName
  }

  getInvestments() {
    this.investmentsService.getInvestments()
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: data => {
          this.allInvestments = data.investments;
          this.filterInvestmentsByCity(this.cityName); // Make sure this uses the updated cityName
        },
        error: error => console.error('Error fetching investments:', error)
      });
  }

  getCities() {
    this.cityService.getCities()
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: data => this.cities = data.cities,
        error: error => console.error('Error fetching cities:', error)
      });
  }

  filterInvestmentsByCity(cityName: string): void {
    console.log('Filtering investments by city:', cityName);
    if (!cityName) {
      this.investments = [...this.allInvestments];
      return;
    }
    this.investments = this.allInvestments.filter(investment => {
      const city = this.cities.find(c => c.name.toLowerCase() === cityName.toLowerCase());
      return city && investment.cityId === city.id;
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
}
