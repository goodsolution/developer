import {Component, OnInit} from '@angular/core';
import {DynamicComponentLoadingServiceService} from "../core/services/dynamic-component-loading-service.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-investment-list',
  templateUrl: './investment-list.component.html',
  styleUrls: ['./investment-list.component.scss']
})
export class InvestmentListComponent implements OnInit {
  cityName: string = '';

  constructor(
    private dynamicLoadingService: DynamicComponentLoadingServiceService, // Only one instance is needed
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    // Reactively listen to route parameter changes
    this.route.paramMap.subscribe(params => {
      const name = params.get('name');
      console.log('Reactive city name:', name); // Check the console to see if the name is correctly logged
      if (name) {
        this.cityName = name;
        // Here, trigger the dynamic loading with the new city name
        this.dynamicLoadingService.triggerInvestmentComponentLoading({cityName: this.cityName});
      }
    });
  }

}
