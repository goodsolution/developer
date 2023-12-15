import {NgModule} from '@angular/core';
import {HeaderComponent} from './components/header/header.component';
import {RouterModule} from "@angular/router";
import {SharedModule} from "../shared/shared.module";
import { FooterComponent } from './components/footer/footer.component';


@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent
  ],
    exports: [
        HeaderComponent,
        FooterComponent
    ],
  imports: [
    SharedModule,
    RouterModule,
  ]
})
export class CoreModule {
}
