import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {DashboardRoutingModule} from './dashboard-routing.module';
import {DashboardComponent} from './dashboard.component';
import {SharedModule} from "../shared/shared.module";
import {SidebarComponent} from './sidebar/sidebar.component';
import {DashboardAdminComponent} from './dashboard-admin/dashboard-admin.component';
import {DashboardDeveloperComponent} from "./dashboard-developer/dashboard-developer.component";
import {DeveloperComponent} from "./developer/developer.component";


@NgModule({
  declarations: [
    DashboardComponent,
    SidebarComponent,
    DeveloperComponent,
    DashboardAdminComponent,
    DashboardDeveloperComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    DashboardRoutingModule,
  ],
  exports: [
    DashboardComponent,
    DashboardAdminComponent,
    DashboardDeveloperComponent
  ]
})
export class DashboardModule { }
