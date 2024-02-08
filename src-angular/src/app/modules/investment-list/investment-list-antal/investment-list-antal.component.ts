import {Component, OnInit} from '@angular/core';
import {InvestmentResponse} from "../../core/models/investment.model";
import {CityResponse} from "../../core/models/city.model";
import {Subject, takeUntil} from "rxjs";
import {InvestmentsService} from "../../core/services/investments.service";
import {ActivatedRoute} from "@angular/router";
import {CitiesService} from "../../core/services/cities.service";

@Component({
  selector: 'app-investment-list-antal',
  templateUrl: './investment-list-antal.component.html',
  styleUrls: ['./investment-list-antal.component.scss']
})
export class InvestmentListAntalComponent implements OnInit {
  allInvestments: InvestmentResponse[] = []; // Holds the complete list of investments
  investments: InvestmentResponse[] = []; // Used for displaying filtered/unfiltered data
  cities: CityResponse[] = [];
  cityName: string = '';
  private unsubscribe$ = new Subject<void>();

  constructor(private service: InvestmentsService, private route: ActivatedRoute, private cityService: CitiesService) {
  }

  getInvestments() {
    this.service.getInvestments()
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: data => {
          this.allInvestments = data.investments;
          this.investments = data.investments;
          this.filterInvestmentsByCity(this.cityName); // Apply initial filter
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

  ngOnInit(): void {
    this.getCities();
    this.getInvestments(); // This should populate allInvestments
    this.route.paramMap.subscribe(params => {
      this.cityName = params.get('name') ?? '';
      // Ensure that investments are loaded before filtering
      if (this.allInvestments.length > 0) {
        this.filterInvestmentsByCity(this.cityName);
      }
    });
  }

  filterInvestmentsByCity(cityName: string): void {
    if (!cityName) {
      // Handle the case where no city is selected initially
      this.investments = [...this.allInvestments];
      console.log('No city selected, copy all investments')
      return;
    }
    const city = this.cities.find(c => c.name === cityName);
    if (city) {
      this.investments = this.allInvestments.filter(investment => investment.cityId === city.id);
      console.log(`Found ${this.investments.length} investments for city ${cityName}`)
    } else {
// If no matching city is found, you can choose to either display all investments or none
      this.investments = [...this.allInvestments]; // or handle differently, e.g., this.investments = [];
      console.log('No matching city found, displaying all investments')
    }
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
}
