import {Component, Input, OnInit} from "@angular/core";
import {Premise} from "../../interfaces/premise";
import {ActivatedRoute} from "@angular/router";
import {PremiseService} from "../../services/premise.service";
import {Location} from '@angular/common';

@Component({
  selector: 'app-premise-detail',
  templateUrl: './premise-detail.component.html',
  styleUrls: ['./premise-detail.component.css']
})
export class PremiseDetailComponent implements  OnInit {
  @Input() premise?: Premise;

  constructor(
    private route: ActivatedRoute,
    private premiseService: PremiseService,
    private location: Location
  ) {
  }

  ngOnInit(): void {
    this.getPremise();
  }

  getPremise(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.premiseService.getPremise(id)
      .subscribe(searchResultPremises => this.premise = searchResultPremises.premisesGetResponse[0]);
  }

  goBack(): void {
    this.location.back();
  }

}
