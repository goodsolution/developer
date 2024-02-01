import {Component, Input} from '@angular/core';
import {PremiseResponse} from "../../core/models/premise.model";

@Component({
  selector: 'app-premise',
  templateUrl: './premise.component.html',
  styleUrls: ['./premise.component.scss']
})
export class PremiseComponent {
@Input() premise!: PremiseResponse;

}
