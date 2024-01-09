import {NgModule} from '@angular/core';

import {SharedModule} from "../shared/shared.module";
import {RouterModule} from "@angular/router";
import {FooterComponent} from "./components/footer/footer.component";
import {TranslateModule} from "@ngx-translate/core";
import {DodeHeaderComponent} from "./components/header/dode-header/dode-header.component";
import {AntalHeaderComponent} from "./components/header/antal-header/antal-header.component";


@NgModule({
  declarations: [

    FooterComponent,
    DodeHeaderComponent,
    AntalHeaderComponent
  ],
  imports: [
    SharedModule,
    RouterModule,
    TranslateModule
  ],
  exports: [

    FooterComponent,
    DodeHeaderComponent,
    AntalHeaderComponent
  ]
})
export class CoreModule {
}
