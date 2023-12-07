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

@NgModule({
  declarations: [
    AppComponent,
    PremisesComponent,
    PremiseDetailComponent,
    NavbarDeveloper,
    NavbarLogoComponent,
    NavbarLangComponent,
    NavbarContactComponent
  ],
  imports: [
    BrowserModule,
    CoreModule,
    AuthModule,
    CitiesModule,
    ContactModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}


