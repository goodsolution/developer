import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {PremisesComponent} from "./premises/premises.component";
import {FormsModule} from "@angular/forms";
import {PremiseDetailComponent} from "./premise-detail/premise-detail.component";
import {MessagesComponent} from "./messages/messages.component";
import {AppRoutingModule} from "./routing.module";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    PremisesComponent,
    PremiseDetailComponent,
    MessagesComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

