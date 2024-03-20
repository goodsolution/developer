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
  premises!: PremiseResponse[];
  investments: InvestmentResponse[] = [];
  private subscription!: Subscription;
  private unsubscribe$ = new Subject<void>();

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
        if (this.investments) {
          this.investments.forEach((investment) => {
            this.fetchInvestmentDescription(investment.id);
          });
        }
      });
  }

  fetchInvestmentDescription(investmentId: number): void {
    this.languageService.getTranslation(investmentId, 'investment', 'description')
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: response => {
          const index = this.investments.findIndex(inv => inv.id === investmentId);
          if (index !== -1) {
            this.investments[index].description = response.translation;
            this.changeDetectorRef.detectChanges();
          }
        },
        error: error => {
          console.error('Error fetching investment description:', error);
        }
      });
  }

  loadInvestmentByPremiseId(premiseId: string) {
    this.investmentService.getInvestmentByPremiseId(premiseId).subscribe({
      next: (response) => {
        this.investments = response.investments;
      },
      error: (error) => console.error('Error fetching investment:', error)
    });
  }

  loadPremiseById(premiseId: string) {
    this.premiseService.getPremiseById(premiseId).subscribe({
      next: (response) => {
        this.premises = response.premisesGetResponse
      },
      error: (error) => console.error('Error fetching premise:', error)
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
    this.subscription.unsubscribe();
  }

}
