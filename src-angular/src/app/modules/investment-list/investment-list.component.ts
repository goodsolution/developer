import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {DynamicComponentLoadingService} from "../core/services/dynamic-component-loading.service";

@Component({
  selector: 'app-investment-list',
  templateUrl: './investment-list.component.html',
  styleUrls: ['./investment-list.component.scss']
})
export class InvestmentListComponent implements OnInit {
  cityName: string = '';

  constructor(
    private dynamicLoadingService: DynamicComponentLoadingService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const name = params.get('name');
      if (name && name !== this.cityName) {
        this.cityName = name;
        this.dynamicLoadingService.triggerInvestmentComponentLoading({cityName: this.cityName});
      }
    });
  }

}
