import {Component, OnInit} from '@angular/core';
import {CodeStatusService} from "../core/services/code-status.service";

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss']
})
export class ContactComponent implements OnInit{

constructor(private codeStatus: CodeStatusService) { }

  ngOnInit(): void {
    this.codeStatus.triggerContactComponentLoading();
  }

}
