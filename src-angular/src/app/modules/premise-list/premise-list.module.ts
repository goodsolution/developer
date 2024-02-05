import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PremiseListRoutingModule } from './premise-list-routing.module';
import { PremiseListComponent } from './premise-list.component';
import { PremiseComponent } from './premise/premise.component';
import {SharedModule} from "../shared/shared.module";
import {InvestmentListModule} from "../investment-list/investment-list.module";


@NgModule({
  declarations: [
    PremiseListComponent,
    PremiseComponent
  ],
    imports: [
        CommonModule,
        PremiseListRoutingModule,
        SharedModule,
        InvestmentListModule
    ],
  exports: [
    PremiseListComponent,
    PremiseComponent
  ]
})
export class PremiseListModule { }
