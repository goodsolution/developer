import { Component } from '@angular/core';
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'developer-angular';

  constructor(private translate: TranslateService) {
    translate.setDefaultLang('en');
    this.translate.use(<string>this.translate.getBrowserLang());
  }

  changeLanguage(lang: string) {
    this.translate.use(lang);
  }

}
