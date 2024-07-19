import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProjectOverviewRoutingModule } from './project-overview-routing.module';
import { ProjectOverviewComponent } from './project-overview.component';
import {SharedModule} from "../shared/shared.module";
import { MainContentComponent } from './main-content/main-content.component';
import { ScreenshotDialogComponent } from './screenshot-dialog/screenshot-dialog.component';
import {FooterComponent} from "./footer-project-overview/footer.component";
import {HeaderComponent} from "./header-project-overview/header.component";


@NgModule({
  declarations: [
    ProjectOverviewComponent,
    FooterComponent,
    HeaderComponent,
    MainContentComponent,
    ScreenshotDialogComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    ProjectOverviewRoutingModule
  ],
  exports: [
    ProjectOverviewComponent
  ]
})
export class ProjectOverviewModule { }
