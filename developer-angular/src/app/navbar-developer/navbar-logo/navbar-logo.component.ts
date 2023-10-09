import {Component, OnInit} from '@angular/core';
import {DeveloperLogoUrlService} from "../../services/developer-logo-url.service";

@Component({
  selector: 'app-navbar-logo',
  templateUrl: './navbar-logo.component.html',
  styleUrls: ['./navbar-logo.component.css']
})
export class NavbarLogoComponent implements OnInit{
  url: String | undefined;

  constructor(private developerLogoUrlService: DeveloperLogoUrlService) {
  }

  getLogo() {
    this.developerLogoUrlService.getLogoUrl().subscribe(url => this.url = url.url)
  }

  ngOnInit(): void {
    this.getLogo();
  }

}
