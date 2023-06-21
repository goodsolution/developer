import {Component, OnInit} from "@angular/core";
import {Premise} from "../premise";
import {PremiseService} from "../premise.service";

@Component({
  selector: 'app-premises',
  templateUrl: './premises.component.html',
  styleUrls: ['./premises.component.css']
})
export class PremisesComponent implements OnInit {
  premises: Premise[] = []

  constructor(private premiseService: PremiseService) {
  }

  ngOnInit(): void {
    this.getPremises();
  }

  getPremises(): void {
    this.premiseService.getPremises().subscribe(premises => this.premises = premises);
  }

}
