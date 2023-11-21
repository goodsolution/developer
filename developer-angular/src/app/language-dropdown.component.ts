import {Component} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-language-dropdown',
  template:
`
    <li class="nav-item dropdown active">
      <a class="nav-link dropdown-toggle" routerLink="#"
         id="navbarDropdownMenuLink"
         data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        {{'Language' | translate}}
      </a>
      <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
        <a class="dropdown-item" href="#" (click)="changeLanguage('en')">{{'English' | translate}}</a>
        <a class="dropdown-item" href="#" (click)="changeLanguage('pl')">{{'Polish' | translate}}</a>
      </div>
    </li>
`
})
export class LanguageDropdownComponent {
  constructor(private translate: TranslateService) {
  }

  changeLanguage(language: string) {
    this.translate.use(language);
  }

  // changeLanguage(event: any) {
  //   const language = event.target.value;
  //   this.translate.use(language);
  // }
}
