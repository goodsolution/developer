import {Component, OnInit} from '@angular/core';
import {CitiesService} from "../core/services/cities.service";
import {CityResponse} from "../core/models/city.model";
import {ActivatedRoute} from "@angular/router";


@Component({
  selector: 'app-cities',
  templateUrl: './cities.component.html',
  styleUrls: ['./cities.component.scss']
})
export class CitiesComponent implements OnInit {

  cities: CityResponse[] = [];
  cityName: string = '';

  constructor(private service: CitiesService, private route: ActivatedRoute) {
  }

  getCities() {
    this.service.getCities().subscribe(data => {
      this.cities = data.cities;
    });
  }

  ngOnInit(): void {
    this.getCities();
    this.route.paramMap.subscribe(params => {
      this.cityName = params.get('name') || '';
    });
  }


}
