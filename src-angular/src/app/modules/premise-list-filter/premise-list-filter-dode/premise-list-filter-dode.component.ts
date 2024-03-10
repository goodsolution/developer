import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {PremiseService} from "../../core/services/premise.service";

export interface PriceFilterCriteria {
  minPrice: number;
  maxPrice: number;
}

@Component({
  selector: 'app-premise-list-filter-dode',
  templateUrl: './premise-list-filter-dode.component.html',
  styleUrls: ['./premise-list-filter-dode.component.scss']
})
export class PremiseListFilterDodeComponent implements OnInit {

  minPrice!: number;
  maxPrice!: number;
  minAllowedPrice!: number;
  maxAllowedPrice!: number;
  @Input() investmentId!: number;
  @Output() priceRangeChange = new EventEmitter<PriceFilterCriteria>();

  constructor(private premiseService: PremiseService) {
  }

  ngOnInit(): void {
    this.loadMinMaxTotalPrice();
  }

  loadMinMaxTotalPrice(): void {
    this.premiseService.getPremiseMinMaxTotalPriceByInvestmentId(this.investmentId)
      .subscribe((response) => {
        this.minPrice = response.minPrice;
        this.maxPrice = response.maxPrice
        this.minAllowedPrice = this.minPrice;
        this.maxAllowedPrice = this.maxPrice;
      });
  }

  onMinPriceChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.minPrice = Number(input.value);
    this.emitPriceRangeChange();
  }

  onMaxPriceChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.maxPrice = Number(input.value);
    this.emitPriceRangeChange();
  }

  private emitPriceRangeChange(): void {
    this.priceRangeChange.emit({
      minPrice: this.minPrice,
      maxPrice: this.maxPrice
    });
  }

  formatPrice(value: number): string {
    if (value >= 1000000) {
      return (value / 1000000).toFixed(1).replace('.', ',') + ' mln';
    } else if (value >= 1000) {
      return (value / 1000).toFixed(1).replace('.', ',') + ' tys.';
    } else {
      return value + ' PLN';
    }
  }

}
