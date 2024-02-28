import {Component, OnInit} from '@angular/core';
import {CityResponse} from "../../../models/city.model";
import {HeaderLogoUrlService} from "../../../services/header-logo-url.service";
import {CitiesService} from "../../../services/cities.service";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-dode-header',
  templateUrl: './dode-header.component.html',
  styleUrls: ['./dode-header.component.scss']
})
export class DodeHeaderComponent implements OnInit {
  url!: string;
  cities: CityResponse[] = [];

  constructor(private headerLogoUrlService: HeaderLogoUrlService,
              private cityService: CitiesService,
              private translate: TranslateService) {
    translate.setDefaultLang('en')
  }

  switchLanguage(language: string) {
    this.translate.use(language);
  }

  private getLogoUrl(): void {
    this.headerLogoUrlService.getLogoUrl().subscribe(data => {
      this.url = data.url;
    });
  }

  private getCities() {
    this.cityService.getCities().subscribe(data => {
      this.cities = data.cities;
    });
  }

  ngOnInit(): void {
    this.getLogoUrl();
    this.getCities();
  }

}
