import {Component, OnInit} from '@angular/core';
import {HeaderLogoUrlService} from "../../services/header-logo-url.service";
import {CityResponse} from "../../models/city.model";
import {CitiesService} from "../../services/cities.service";
import {CodeStatusService} from "../../services/code-status.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  url!: string;
  cities: CityResponse[] = [];
  code!: string;

  constructor(private service: HeaderLogoUrlService,
              private cityService: CitiesService,
              private codeService: CodeStatusService) {
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
