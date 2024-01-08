import {Component, OnInit} from '@angular/core';
import {InvestmentResponse} from "../core/models/investment.model";
import {InvestmentsService} from "../core/services/investments.service";
import {ActivatedRoute} from "@angular/router";
import {CitiesService} from "../core/services/cities.service";
import {CityResponse} from "../core/models/city.model";

@Component({
  selector: 'app-investment-list',
  templateUrl: './investment-list.component.html',
  styleUrls: ['./investment-list.component.scss']
})
export class InvestmentListComponent implements OnInit{

  investments: InvestmentResponse[] = [];
  cities: CityResponse[] = [];
  cityName: string = '';
  constructor(private service: InvestmentsService, private route: ActivatedRoute, private cityService: CitiesService) {
  }

  getInvestments() {
    this.service.getInvestments().subscribe(data => {
      this.investments = data.investments;
    });
  }

  getCities() {
    this.cityService.getCities().subscribe(data => {
      this.cities = data.cities;
    });
  }

  ngOnInit(): void {
    this.getInvestments();
    this.route.paramMap.subscribe(params => {
      this.cityName = params.get('name') || '';
    });
  }




}
