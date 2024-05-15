import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {DeveloperRoutingModule} from './developer-routing.module';
import {SharedModule} from "../shared/shared.module";
import {DeveloperAntalComponent} from './developer-antal/developer-antal.component';
import {DeveloperDodeComponent} from './developer-dode/developer-dode.component';


@NgModule({
  declarations: [
    DeveloperAntalComponent,
    DeveloperDodeComponent
  ],
  imports: [
    CommonModule,
    DeveloperRoutingModule,
    SharedModule
  ],
  exports: [
    DeveloperAntalComponent,
    DeveloperDodeComponent
  ]
})
export class DeveloperModule { }
