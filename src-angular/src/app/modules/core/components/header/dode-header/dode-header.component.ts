import {Component, OnInit} from '@angular/core';
import {CityResponse} from "../../../models/city.model";
import {HeaderLogoUrlService} from "../../../services/header-logo-url.service";
import {CitiesService} from "../../../services/cities.service";
import {CodeStatusService} from "../../../services/code-status.service";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-dode-header',
  templateUrl: './dode-header.component.html',
  styleUrls: ['./dode-header.component.scss']
})
export class DodeHeaderComponent implements OnInit {
  url!: string;
  cities: CityResponse[] = [];
  code!: string;

  constructor(private service: HeaderLogoUrlService,
              private cityService: CitiesService,
              private codeService: CodeStatusService,
              private translate: TranslateService) {
    translate.setDefaultLang('en'); // set a default language
  }

  switchLanguage(language: string) {
    this.translate.use(language); // switch to selected language
  }

  getCode() {
    this.codeService.getDeveloperStatus().subscribe(data => {
      this.code = data.code;
    });
  }

  getLogoUrl(): void {
    this.service.getLogoUrl().subscribe(data => {
      this.url = data.url;
    });
  }

  getCities() {
    this.cityService.getCities().subscribe(data => {
      this.cities = data.cities;
    });
  }

  ngOnInit(): void {
    this.getCode();
    this.getLogoUrl();
    this.getCities();
  }

}
