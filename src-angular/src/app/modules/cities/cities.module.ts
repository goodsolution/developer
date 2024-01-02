import {NgModule} from '@angular/core';

import {CitiesRoutingModule} from './cities-routing.module';
import {CitiesComponent} from './cities.component';
import {SharedModule} from "../shared/shared.module";


@NgModule({
  declarations: [
    CitiesComponent
  ],
  imports: [
    SharedModule,
    CitiesRoutingModule
  ],
  exports: [
    CitiesComponent
  ]
})
export class CitiesModule {
}
