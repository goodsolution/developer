import {Component} from "@angular/core";
import {City} from "../city";
import {CityService} from "../city.service";

@Component({
  selector: 'app-navbar-developer-cities',
  templateUrl: './navbar-developer-cities.component.html',
  styleUrls: ['./navbar-developer-cities.component.css']
})
export class NavbarDeveloperCitiesComponent {
  title = 'Cities';
  cities: City[] = [];
  constructor(private cityService: CityService) {
  }

  ngOnInit(): void {
    this.getCities();
  }

  getCities(): void {
    this.cityService.getCities()
      .subscribe(searchResultCity=> this.cities = searchResultCity.cities);
  }
}
