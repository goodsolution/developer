import {Component, EventEmitter, Output} from '@angular/core';
import {Member} from "../../shared/interfaces/member";

@Component({
  selector: 'app-add-member',
  templateUrl: './add-member.component.html',
  styleUrls: ['./add-member.component.css']
})
export class AddMemberComponent {
  @Output() addMember = new EventEmitter<Member>()

  memberFirstName = '';
  memberLastName = '';

  addNewMember() {
    this.addMember.emit({firstName: this.memberFirstName, lastName: this.memberLastName})
  }
}
