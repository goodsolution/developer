import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FiltersRoutingModule } from './filters-routing.module';
import { FiltersDodeComponent } from './filters-dode/filters-dode.component';
import { FiltersAntalComponent } from './filters-antal/filters-antal.component';


@NgModule({
  declarations: [
    FiltersDodeComponent,
    FiltersAntalComponent
  ],
  imports: [
    CommonModule,
    FiltersRoutingModule
  ],
  exports: [
    FiltersDodeComponent,
    FiltersAntalComponent
  ]
})
export class FiltersModule { }
