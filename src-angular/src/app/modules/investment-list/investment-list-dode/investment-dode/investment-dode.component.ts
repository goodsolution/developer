import {Component, Input} from '@angular/core';
import {InvestmentResponse} from "../../../core/models/investment.model";

@Component({
  selector: 'app-investment-dode',
  templateUrl: './investment-dode.component.html',
  styleUrls: ['./investment-dode.component.scss']
})
export class InvestmentDodeComponent {
  @Input() investment!: InvestmentResponse;

}
