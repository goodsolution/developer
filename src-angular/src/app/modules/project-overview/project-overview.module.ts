import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ProjectOverviewRoutingModule} from './project-overview-routing.module';
import {ProjectOverviewComponent} from './project-overview.component';
import {SharedModule} from "../shared/shared.module";
import {MainContentComponent} from './main-content/main-content.component';
import {ScreenshotDialogComponent} from './screenshot-dialog/screenshot-dialog.component';
import {HeaderProjectOverviewComponent} from './header-project-overview/header-project-overview.component';
import {FooterProjectOverviewComponent} from './footer-project-overview/footer-project-overview.component';

@NgModule({
  declarations: [
    ProjectOverviewComponent,
    MainContentComponent,
    ScreenshotDialogComponent,
    HeaderProjectOverviewComponent,
    FooterProjectOverviewComponent
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
export class ProjectOverviewModule {
}
