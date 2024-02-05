import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {InvestmentListRoutingModule} from './investment-list-routing.module';
import {InvestmentListComponent} from './investment-list.component';
import {InvestmentComponent} from './investment/investment.component';
import {SharedModule} from "../shared/shared.module";


@NgModule({
  declarations: [
    InvestmentListComponent,
    InvestmentComponent
  ],
  imports: [
    CommonModule,
    InvestmentListRoutingModule,
    SharedModule
  ],
  exports: [
    InvestmentListComponent,
    InvestmentComponent
  ]
})
export class InvestmentListModule {
}
