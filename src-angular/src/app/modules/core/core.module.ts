import {NgModule} from '@angular/core';
import {HeaderComponent} from './components/header/header.component';
import {SharedModule} from "../shared/shared.module";
import {RouterModule} from "@angular/router";
import {FooterComponent} from "./components/footer/footer.component";
import {TranslateModule} from "@ngx-translate/core";


@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent
  ],
    imports: [
        SharedModule,
        RouterModule,
        TranslateModule
    ],
  exports: [
    HeaderComponent,
    FooterComponent
  ]
})
export class CoreModule {
}
