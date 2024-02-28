import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {PremiseListFilterRoutingModule} from './premise-list-filter-routing.module';
import {PremiseListFilterDodeComponent} from './premise-list-filter-dode/premise-list-filter-dode.component';
import {PremiseListFilterAntalComponent} from './premise-list-filter-antal/premise-list-filter-antal.component';
import {SharedModule} from "../shared/shared.module";

@NgModule({
  declarations: [
    PremiseListFilterDodeComponent,
    PremiseListFilterAntalComponent
  ],
  imports: [
    CommonModule,
    PremiseListFilterRoutingModule,
    SharedModule
  ],
  exports: [
    PremiseListFilterDodeComponent,
    PremiseListFilterAntalComponent
  ]
})
export class PremiseListFilterModule {
}
