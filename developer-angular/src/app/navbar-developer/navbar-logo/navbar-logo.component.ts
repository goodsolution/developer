import {Component, OnInit} from '@angular/core';
import {DeveloperLogoUrlService} from "../../services/developer-logo-url.service";

@Component({
  selector: 'app-navbar-logo',
  templateUrl: './navbar-logo.component.html',
  styleUrls: ['./navbar-logo.component.css']
})
export class NavbarLogoComponent implements OnInit{
  apiUrl!: string;

  constructor(private developerLogoUrlService: DeveloperLogoUrlService) {
  }

  getLogo() {
    this.developerLogoUrlService.getLogoUrl().subscribe(url => this.apiUrl = url.url)
  }

  ngOnInit(): void {
    this.getLogo();
  }

}
