import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './dashboard.component';
import { DashboardDodeComponent } from './dashboard-dode/dashboard-dode.component';
import { DashboardAntalComponent } from './dashboard-antal/dashboard-antal.component';
import {SharedModule} from "../shared/shared.module";


@NgModule({
  declarations: [
    DashboardComponent,
    DashboardDodeComponent,
    DashboardAntalComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    DashboardRoutingModule
  ],
  exports: [
    DashboardComponent,
    DashboardDodeComponent,
    DashboardAntalComponent
  ]
})
export class DashboardModule { }
