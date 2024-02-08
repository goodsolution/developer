import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {InvestmentListRoutingModule} from './investment-list-routing.module';
import {InvestmentListComponent} from './investment-list.component';
import {InvestmentComponent} from './investment/investment.component';
import {SharedModule} from "../shared/shared.module";
import { InvestmentListDodeComponent } from './investment-list-dode/investment-list-dode.component';
import { InvestmentListAntalComponent } from './investment-list-antal/investment-list-antal.component';
import { InvestmentAntalComponent } from './investment-list-antal/investment-antal/investment-antal.component';
import {InvestmentDodeComponent} from "./investment-list-dode/investment-dode/investment-dode.component";



@NgModule({
  declarations: [
    InvestmentListComponent,
    InvestmentComponent,
    InvestmentListDodeComponent,
    InvestmentListAntalComponent,
    InvestmentAntalComponent,
    InvestmentDodeComponent
  ],
  imports: [
    CommonModule,
    InvestmentListRoutingModule,
    SharedModule
  ],
  exports: [
    InvestmentListComponent,
    InvestmentComponent,
    InvestmentListDodeComponent,
    InvestmentListAntalComponent,
    InvestmentAntalComponent,
    InvestmentDodeComponent
  ]
})
export class InvestmentListModule {
}
