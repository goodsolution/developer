import {APP_INITIALIZER, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CoreModule} from "./modules/core/core.module";
import {HomeModule} from "./modules/home/home.module";
import {AuthModule} from "./modules/auth/auth.module";
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {TranslateLoader, TranslateModule, TranslateService} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {ContactModule} from "./modules/contact/contact.module";
import {PremiseListModule} from "./modules/premise-list/premise-list.module";
import {CustomReuseStrategyService} from "./modules/core/routing/custom-reuse-strategy.service";
import {RouteReuseStrategy} from "@angular/router";
import {ConfigService} from "./modules/core/services/config.service";
import {PremiseListFilterModule} from "./modules/premise-list-filter/premise-list-filter.module";
import {InvestmentListModule} from "./modules/investment-list/investment-list.module";
import {PremiseDetailModule} from "./modules/premise-detail/premise-detail.module";
import {AcceptLanguageInterceptor} from "./modules/core/services/accept-language.interceptor";
import {LanguageService} from "./modules/core/services/language.service";

export function initializeApp(config: ConfigService) {
  return (): Promise<any> => {
    return config.loadAppConfig();
  }
}

export function appInit(languageService: LanguageService, translate: TranslateService) {
  return (): Promise<any> => {
    return new Promise((resolve) => {
      const browserLang = translate.getBrowserLang();
      const langToUse = localStorage.getItem('preferredLanguage') || browserLang || 'en';
      translate.setDefaultLang(langToUse);
      languageService.setLanguage(langToUse);
      translate.use(langToUse).subscribe(() => {
        resolve(null);
      });
    });
  };
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
    PremiseListFilterModule,
    PremiseDetailModule,
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
    ConfigService,
    {
      provide: APP_INITIALIZER,
      useFactory: initializeApp,
      deps: [ConfigService],
      multi: true
    },
    {
      provide: APP_INITIALIZER,
      useFactory: appInit,
      deps: [LanguageService, TranslateService],
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AcceptLanguageInterceptor,
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
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}
