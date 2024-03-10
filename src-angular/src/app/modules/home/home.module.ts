import {NgModule} from '@angular/core';

import {HomeRoutingModule} from './home-routing.module';
import {HomeComponent} from './home.component';
import {SharedModule} from "../shared/shared.module";
import { HomeDodeComponent } from './home-dode/home-dode.component';
import { HomeAntalComponent } from './home-antal/home-antal.component';

@NgModule({
  declarations: [
    HomeComponent,
    HomeDodeComponent,
    HomeAntalComponent
  ],
    imports: [
        SharedModule,
        HomeRoutingModule
    ],
  exports: [
    HomeComponent
  ]
})
export class HomeModule {
}
