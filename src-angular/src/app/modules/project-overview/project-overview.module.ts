import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProjectOverviewRoutingModule } from './project-overview-routing.module';
import { ProjectOverviewComponent } from './project-overview.component';
import {SharedModule} from "../shared/shared.module";
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { MainContentComponent } from './main-content/main-content.component';
import { ScreenshotDialogComponent } from './screenshot-dialog/screenshot-dialog.component';


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
  ]
})
export class ProjectOverviewModule { }
