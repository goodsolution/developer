import { Component } from '@angular/core';
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-navbar-lang',
  templateUrl: './navbar-lang.component.html',
  styleUrls: ['./navbar-lang.component.css']
})
export class NavbarLangComponent {

  constructor(private translate: TranslateService) {}

  changeLanguage(language: string) {
    this.translate.use(language);
  }

}
