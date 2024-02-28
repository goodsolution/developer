import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, Subscription, takeUntil } from 'rxjs';
import { PremiseResponse } from "../../core/models/premise.model";
import { PremiseService } from "../../core/services/premise.service";
import { DynamicComponentLoadingService } from "../../core/services/dynamic-component-loading.service";


// Define filter criteria interface
interface PriceFilterCriteria {
  minPrice: number;
  maxPrice: number;
}


@Component({
  selector: 'app-premise-list-dode',
  templateUrl: './premise-list-dode.component.html',
  styleUrls: ['./premise-list-dode.component.scss']
})
export class PremiseListDodeComponent implements OnInit, OnDestroy {
  premises: PremiseResponse[] = [];
  filteredPremises: PremiseResponse[] = [];
  investmentId!: number;
  private unsubscribe$ = new Subject<void>();
  private subscription!: Subscription;
  filterCriteria: PriceFilterCriteria = { minPrice: 0, maxPrice: Infinity };

  constructor(
    private premiseService: PremiseService,
    private dynamicLoadingService: DynamicComponentLoadingService
  ) {}

  ngOnInit(): void {
    this.subscription = this.dynamicLoadingService.getPremiseComponentTrigger()
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (data) => {
          this.loadPremises(data.investmentId);
          this.investmentId = data.investmentId;
        },
        error: (error) => console.error('Error in dynamic loading of premises:', error)
      });
  }

  private loadPremises(investmentId: number) {
    this.premiseService.getPremisesByInvestmentId(investmentId).subscribe({
      next: (response) => {
        this.premises = response.premisesGetResponse;
        this.applyFilters();
      },
      error: (error) => console.error('Error fetching premises:', error)
    });
  }

  onPriceRangeChange(criteria: PriceFilterCriteria): void {
    this.filterCriteria = criteria;
    this.applyFilters();
  }

  private applyFilters(): void {
    this.filteredPremises = this.premises.filter(premise =>
      premise.totalPrice >= this.filterCriteria.minPrice &&
      premise.totalPrice <= this.filterCriteria.maxPrice
    );
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
    this.subscription.unsubscribe();
  }

}
