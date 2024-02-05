import {Component, Input} from '@angular/core';
import {InvestmentResponse} from "../../core/models/investment.model";

@Component({
  selector: 'app-investment',
  templateUrl: './investment.component.html',
  styleUrls: ['./investment.component.scss']
})
export class InvestmentComponent {
  @Input() investment!: InvestmentResponse;

}
