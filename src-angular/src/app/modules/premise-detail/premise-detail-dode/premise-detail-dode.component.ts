import {Component, Input} from '@angular/core';
import {Premise} from "../../../../../../db/src/app/premise";

@Component({
  selector: 'app-premise-detail-dode',
  templateUrl: './premise-detail-dode.component.html',
  styleUrls: ['./premise-detail-dode.component.scss']
})
export class PremiseDetailDodeComponent {
@Input() premise!: Premise

}
