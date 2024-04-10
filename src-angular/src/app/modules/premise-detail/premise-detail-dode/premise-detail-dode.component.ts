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
            const fullKey = `${domain}.${key}`;
            console.log(`Fetching: ${fullKey}`, response);
            this.labels[fullKey] = response.translation;
          },
          error: (error) => console.error(`Error fetching dictionary data for domain: ${domain}, key: ${key}`, error)
        });
    });
  }

  determineAndFetchLabelsForPremise(premise: PremiseResponse): void {
    this.premises.forEach(premise => {
      // List of premise attributes to translate
      const attributesToTranslate = ['salesStatus', 'technicalStatus', 'exposure'];

      attributesToTranslate.forEach(attribute => {
        // Assuming the backend requires the domain to be in a specific format
        // e.g., 'premises.sales_status' for the 'salesStatus' attribute
        const domain = `premises.${attribute}`;
        const value = premise[attribute as keyof typeof premise];

        // Ensure value is a string and proceed to fetch its translation
        if (typeof value === 'string') {
          this.fetchDictionaryLabels([value], domain);
        }
      });
    });
  }

  loadPremiseById(premiseId: string) {
    this.premiseService.getPremiseById(premiseId).subscribe({
      next: (response) => {
        this.premises = response.premisesGetResponse || [];
        this.premises.forEach(premise => {
          this.determineAndFetchLabelsForPremise(premise);
        });
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

  getLabel(attribute: 'technicalStatus' | 'salesStatus' | 'exposure', premise: PremiseResponse): string {
    const key = `premises.${attribute}.${premise[attribute]}`;
    if (!this.labels[key]) {
      console.warn(`Translation not found for key: ${key}`);
      return `Translation not found for ${attribute}`;
    }
    return this.labels[key];
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
    this.subscription.unsubscribe();
  }

}
