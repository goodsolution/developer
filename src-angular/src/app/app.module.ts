import {APP_INITIALIZER, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CoreModule} from "./modules/core/core.module";
import {HomeModule} from "./modules/home/home.module";
import {AuthModule} from "./modules/auth/auth.module";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {InvestmentListModule} from "./modules/investment-list/investment-list.module";
import {ContactModule} from "./modules/contact/contact.module";
import {PremiseListModule} from "./modules/premise-list/premise-list.module";
import {CustomReuseStrategyService} from "./modules/core/routing/custom-reuse-strategy.service";
import {RouteReuseStrategy} from "@angular/router";
import {CodeStatusService} from "./modules/core/services/code-status.service";

// Function to return a configuration loading function
export function initializeApp(config: CodeStatusService) {
  return (): Promise<any> => {
    return config.loadConfig();
  }
}

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HomeModule,
    ContactModule,
    AuthModule,
    CoreModule,
    InvestmentListModule,
    PremiseListModule,
    AppRoutingModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
  ],
  providers: [
    CodeStatusService,
    {
      provide: APP_INITIALIZER,
      useFactory: initializeApp,
      deps: [CodeStatusService],
      multi: true
    },
    {
      provide: RouteReuseStrategy,
      useClass: CustomReuseStrategyService
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}
