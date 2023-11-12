import {Component, OnInit} from "@angular/core";
import {Premise} from "../shared/interfaces/premise";
import {PremiseService} from "../core/services/premise.service";

@Component({
  selector: 'app-premises',
  templateUrl: './premises.component.html',
  styleUrls: ['./premises.component.css']
})
export class PremisesComponent implements OnInit {
  premises: Premise[] = [];

  constructor(private premiseService: PremiseService) {
  }

  ngOnInit(): void {
    this.getPremises();
  }

  getPremises(): void {
    this.premiseService.getPremises()
      .subscribe(searchResultPremises=> this.premises = searchResultPremises.premisesGetResponse);
  }

}
