import {Component, OnInit} from '@angular/core';
import {CodeStatusService} from "../core/services/code-status.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  code!: string;

  constructor(
    private codeStatus: CodeStatusService) {
  }

  ngOnInit(): void {
    this.codeStatus.getHomeComponentTrigger();
  }

}
