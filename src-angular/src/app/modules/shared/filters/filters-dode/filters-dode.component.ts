import {Component, EventEmitter, Input, Output} from '@angular/core';

export interface PriceFilterCriteria {
  minPrice: number;
  maxPrice: number;
}

@Component({
  selector: 'app-filters-dode',
  templateUrl: './filters-dode.component.html',
  styleUrls: ['./filters-dode.component.scss']
})
export class FiltersDodeComponent {

  minPrice: number = 1000000;
  maxPrice: number = 10000000;

  @Output() priceRangeChange = new EventEmitter<PriceFilterCriteria>();

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

}
