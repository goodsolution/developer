import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DeveloperRoutingModule } from './developer-routing.module';
import {SharedModule} from "../shared/shared.module";
import {DeveloperComponent} from "./developer.component";


@NgModule({
  declarations: [
    DeveloperComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    DeveloperRoutingModule
  ],
  exports: [
    DeveloperComponent
  ]
})
export class DeveloperModule { }
