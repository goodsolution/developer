import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Subject, Subscription, takeUntil} from 'rxjs';
import {PremiseResponse} from "../../core/models/premise.model";
import {PremiseService} from "../../core/services/premise.service";
import {DynamicComponentLoadingService} from "../../core/services/dynamic-component-loading.service";
import {MatPaginator} from "@angular/material/paginator";

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
export class PremiseListDodeComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  currentPage!: number
  pageSize!: number
  premises: PremiseResponse[] = [];
  pagedPremises: PremiseResponse[] = [];
  filteredPremises: PremiseResponse[] = [];
  investmentId!: number;
  private unsubscribe$ = new Subject<void>();
  private subscription!: Subscription;
  filterCriteria: PriceFilterCriteria = {minPrice: 0, maxPrice: Infinity};

  constructor(
    private premiseService: PremiseService,
    private dynamicLoadingService: DynamicComponentLoadingService
  ) {
  }

  ngOnInit(): void {
    this.currentPage = 0;
    this.pageSize = 10;
    this.subscription = this.dynamicLoadingService.getPremiseComponentTrigger()
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (data) => {
          this.loadPremises(data.investmentId);
          this.investmentId = data.investmentId;
        },
        error: (error) => console.error('Error in dynamic loading of premises:', error)
      });
    setTimeout(() => {
      if (this.paginator) {
        this.paginator.pageIndex = this.currentPage;
        this.paginator.pageSize = this.pageSize;
        this.paginator.length = this.filteredPremises.length;
      }
    });
  }

  ngAfterViewInit() {
    this.paginator.page
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (event) => {
          this.currentPage = event.pageIndex;
          this.pageSize = event.pageSize;
          this.updatePagedPremises();
        },
        error: (error) => console.error('Error in paginator:', error)
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
    if (this.paginator) {
      this.paginator.length = this.filteredPremises.length;
      this.paginator.pageIndex = 0;
    }
    this.updatePagedPremises();
  }

  private updatePagedPremises(): void {
    const startIndex = this.currentPage * this.pageSize;
    this.pagedPremises = this.filteredPremises.slice(startIndex, startIndex + this.pageSize);
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
    this.subscription.unsubscribe();
  }

}
