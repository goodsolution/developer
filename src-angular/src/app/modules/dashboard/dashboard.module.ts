import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './dashboard.component';
import { DashboardDodeComponent } from './dashboard-dode/dashboard-dode.component';
import { DashboardAntalComponent } from './dashboard-antal/dashboard-antal.component';
import {SharedModule} from "../shared/shared.module";
import { SidebarComponent } from './sidebar/sidebar.component';
import {DeveloperModule} from "../developer/developer.module";


@NgModule({
  declarations: [
    DashboardComponent,
    DashboardDodeComponent,
    DashboardAntalComponent,
    SidebarComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    DashboardRoutingModule,
    DeveloperModule
  ],
  exports: [
    DashboardComponent,
    DashboardDodeComponent,
    DashboardAntalComponent
  ]
})
export class DashboardModule { }
