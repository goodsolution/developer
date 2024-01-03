import {Component, OnInit} from '@angular/core';
import {HeaderLogoUrlService} from "../../services/header-logo-url.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  url!: string;

  constructor(private service: HeaderLogoUrlService) {
  }

  getLogoUrl(): void {
    this.service.getLogoUrl().subscribe(data => {
      this.url = data.url;
    });
  }

  ngOnInit(): void {
    this.getLogoUrl();
  }




}
