import {Component, OnInit} from '@angular/core';
import {CitiesService} from "../core/services/cities.service";
import {CityResponse} from "../core/models/city.model";


@Component({
  selector: 'app-cities',
  templateUrl: './cities.component.html',
  styleUrls: ['./cities.component.scss']
})
export class CitiesComponent implements OnInit{

  cities: CityResponse[] = [];
  constructor(private service: CitiesService) {}

  getCities() {
    this.service.getCities().subscribe(data => {
      this.cities = data.cities;
    });
  }

  ngOnInit(): void {
    this.getCities();
  }


}
