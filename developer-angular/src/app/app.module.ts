import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {PremisesComponent} from "./premises/premises.component";
import {FormsModule} from "@angular/forms";
import {PremiseDetailComponent} from "./premises/premise-detail/premise-detail.component";
import {AppRoutingModule} from "./routing.module";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatListModule} from "@angular/material/list";
import {NgOptimizedImage} from "@angular/common";
import {NavbarDeveloper} from "./navbar-developer/navbar-developer.component";
import {NavbarLogoComponent} from './navbar-developer/navbar-logo/navbar-logo.component';
import {NavbarLangComponent} from "./navbar-developer/navbar-lang/navbar-lang.component";
import {ContactComponent} from "./contact/contact.component";
import {MemberListComponent} from "./member-list/member-list.component";
import {AddMemberComponent} from "./member-list/add-member/add-member.component";
import {MemberComponent} from "./member-list/member/member.component";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {CustomTranslationLoader} from "./core/services/custom-translation-loader.service";
import {LanguageDropdownComponent} from "./language-dropdown.component";

@NgModule({
  declarations: [
    AppComponent,
    PremisesComponent,
    PremiseDetailComponent,
    NavbarDeveloper,
    NavbarLogoComponent,
    NavbarLangComponent,
    ContactComponent,
    MemberListComponent,
    AddMemberComponent,
    MemberComponent,
    LanguageDropdownComponent
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
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (http: HttpClient) => new CustomTranslationLoader(http, './assets/i18n/', '.json'),
        deps: [HttpClient],
      },
    }),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }


