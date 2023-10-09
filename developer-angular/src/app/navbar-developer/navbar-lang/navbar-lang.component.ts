import { Component } from '@angular/core';

@Component({
  selector: 'app-navbar-lang',
  templateUrl: './navbar-lang.component.html',
  styleUrls: ['./navbar-lang.component.css']
})
export class NavbarLangComponent {


  changeLanguage(lang: string) {
    window.location.href = window.location.pathname + "?lang=" + lang;
  }


}
