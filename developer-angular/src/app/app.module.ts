import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {PremisesComponent} from "./premises/premises.component";
import {PremiseDetailComponent} from "./premises/premise-detail/premise-detail.component";
import {AppRoutingModule} from "./app-routing.module";
import {NavbarDeveloper} from "./navbar-developer/navbar-developer.component";
import {NavbarLogoComponent} from './navbar-developer/navbar-logo/navbar-logo.component';
import {NavbarLangComponent} from "./navbar-developer/navbar-lang/navbar-lang.component";
import {NavbarContactComponent} from "./navbar-developer/navbar-contact/navbar-contact.component";
import {CoreModule} from "./modules/core/core.module";
import {AuthModule} from "./modules/auth/auth.module";
import {CitiesModule} from "./modules/cities/cities.module";
import {ContactModule} from "./modules/contact/contact.module";
import {HttpClient} from "@angular/common/http";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import { NavbarMenuComponent } from './navbar-developer/navbar-menu/navbar-menu.component';
import { NavbarLoginComponent } from './navbar-developer/navbar-login/navbar-login.component';

@NgModule({
  declarations: [
    AppComponent,
    PremisesComponent,
    PremiseDetailComponent,
    NavbarDeveloper,
    NavbarLogoComponent,
    NavbarLangComponent,
    NavbarContactComponent,
    NavbarMenuComponent,
    NavbarLoginComponent
  ],
  imports: [
    BrowserModule,
    CoreModule,
    AuthModule,
    CitiesModule,
    ContactModule,
    AppRoutingModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, 'angular/pl/assets/i18n/', '.json');
}




