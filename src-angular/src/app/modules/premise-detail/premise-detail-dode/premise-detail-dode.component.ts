import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {PremiseService} from "../../core/services/premise.service";
import {DynamicComponentLoadingService} from "../../core/services/dynamic-component-loading.service";
import {Subject, Subscription, takeUntil} from "rxjs";
import {PremiseResponse} from "../../core/models/premise.model";
import {InvestmentResponse} from "../../core/models/investment.model";
import {InvestmentsService} from "../../core/services/investments.service";
import {LanguageService} from "../../core/services/language.service";

@Component({
  selector: 'app-premise-detail-dode',
  templateUrl: './premise-detail-dode.component.html',
  styleUrls: ['./premise-detail-dode.component.scss']
})
export class PremiseDetailDodeComponent implements OnInit, OnDestroy {
  premises: PremiseResponse[] = [];
  investments: InvestmentResponse[] = [];
  private subscription!: Subscription;
  private unsubscribe$ = new Subject<void>();
  labels: { [key: string]: string } = {};

  constructor(
    private premiseService: PremiseService,
    private investmentService: InvestmentsService,
    private dynamicLoadingService: DynamicComponentLoadingService,
    private languageService: LanguageService,
    private changeDetectorRef: ChangeDetectorRef
  ) {
  }

  ngOnInit() {
    this.subscription = this.dynamicLoadingService.getPremiseDetailComponentTrigger()
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (data) => {
          this.loadPremiseById(data.premiseId);
          this.loadInvestmentByPremiseId(data.premiseId);
        },
        error: (error) => console.error('Error in dynamic loading of premise:', error)
      });
    this.languageService.language$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(() => {
        if (this.premises && this.premises.length > 0) {
          this.premises.forEach(premise => this.determineAndFetchLabelsForPremise(premise));
        }
        if (this.investments && this.investments.length > 0) {
          this.investments.forEach((investment, index) => {
            this.fetchInvestmentDescription(index);
          });
        }
      });
  }

  fetchDictionaryLabels(keys: string[], domain: string): void {
    keys.forEach(key => {
      this.languageService.getDictionary(domain, key)
        .pipe(takeUntil(this.unsubscribe$))
        .subscribe({
          next: (response) => {
            this.labels[key] = response.translation;
          },
          error: (error) => console.error(`Error fetching dictionary data for domain: ${domain}, key: ${key}`, error)
        });
    });
  }

  determineAndFetchLabelsForPremise(premise: PremiseResponse): void {
    // // Example: Determine keys based on some attributes of the premise
    // const domain = 'premises.sales_status'; // This could also be dynamic if needed
    // const keys = ['a', 'n']; // Determine these keys dynamically
    // this.fetchDictionaryLabels(keys, domain);
    // Let's say we're fetching a label based on the premise's current sales status
    const domain = 'premises.sales_status';
    // Here we use an attribute of premise to determine which keys to fetch
    const keys = [premise.salesStatus]; // Assume salesStatus is 'a' or 'n'
    this.fetchDictionaryLabels(keys, domain);
  }

  loadPremiseById(premiseId: string) {
    this.premiseService.getPremiseById(premiseId).subscribe({
      next: (response) => {
        this.premises = response.premisesGetResponse || [];
        this.premises.forEach(premise => this.determineAndFetchLabelsForPremise(premise));
      },
      error: (error) => console.error('Error fetching premise:', error)
    });
  }

  loadInvestmentByPremiseId(premiseId: string) {
    this.investmentService.getInvestmentByPremiseId(premiseId).subscribe({
      next: (response) => {
        if(Array.isArray(response.investments) && response.investments.length > 0){
          this.investments = response.investments;
          // Initialize translation fetching for all investments after they are loaded
          this.investments.forEach((_, index) => this.fetchInvestmentDescription(index));
        }
      },
      error: (error) => console.error('Error fetching investment:', error)
    });
  }

  fetchInvestmentDescription(index: number): void {
    const investmentId = this.investments[index].id;
    this.languageService.getTranslation(investmentId, 'investment', 'description')
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: response => {
          this.investments[index].description = response.translation;
          this.changeDetectorRef.detectChanges();
        },
        error: error => {
          console.error('Error fetching investment description:', error);
        }
      });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
    this.subscription.unsubscribe();
  }

}
