import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {PremiseService} from "../core/services/premise.service";
import {PremiseResponse} from "../core/models/premise.model";

@Component({
  selector: 'app-premise-list',
  templateUrl: './premise-list.component.html',
  styleUrls: ['./premise-list.component.scss']
})
export class PremiseListComponent {

  premises: PremiseResponse[] = [];

  constructor(
    private route: ActivatedRoute,
    private premiseService: PremiseService
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      const id = +params['id'];
      // console.log('Investment id:', id);
      this.premiseService.getPremisesByInvestmentId(id).subscribe({
        next: (data) => {
          this.premises = data.premisesGetResponse; // Assuming the response structure has a premises property
          // console.log('Premises fetched:', this.premises);
        },
        error: (error) => {
          // console.error('Error fetching premises:', error);
        },
        complete: () => {
          // console.log('Fetching premises complete');
        }
      });
    });
  }


}
