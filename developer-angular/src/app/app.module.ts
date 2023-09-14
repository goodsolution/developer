import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {PremisesComponent} from "./premises/premises.component";
import {FormsModule} from "@angular/forms";
import {PremiseDetailComponent} from "./premise-detail/premise-detail.component";
import {MessagesComponent} from "./messages/messages.component";
import {AppRoutingModule} from "./routing.module";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatListModule} from "@angular/material/list";
import {NgOptimizedImage} from "@angular/common";
import {NavbarDeveloper} from "./navbar-developer/navbar-developer.component";
@NgModule({
  declarations: [
    AppComponent,
    PremisesComponent,
    PremiseDetailComponent,
    MessagesComponent,
    DashboardComponent,
    NavbarDeveloper
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatListModule,
    NgOptimizedImage,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }


