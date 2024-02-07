import {Component, OnInit} from '@angular/core';
import {Subject} from "rxjs";

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss']
})
export class ContactComponent implements OnInit {

  private contactComponentTrigger: Subject<void> = new Subject<void>();

  ngOnInit(): void {
    this.triggerContactComponentLoading();
  }

  triggerContactComponentLoading() {
    this.contactComponentTrigger.next();
  }

}
