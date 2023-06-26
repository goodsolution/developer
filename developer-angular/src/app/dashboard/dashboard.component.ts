import {Component, OnInit} from "@angular/core";
import {Premise} from "../premise";
import {PremiseService} from "../premise.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit{
  premises: Premise[] = [];

  constructor(private premiseService: PremiseService) {
  }

  ngOnInit(): void {
    this.getChosenPremises();
  }

  getChosenPremises(): void {
    this.premiseService.getPremises().subscribe(premises => this.premises = premises.slice(1, 5));
  }

}
