import {Component, OnInit} from '@angular/core';
import {Subject} from "rxjs";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  private homeComponentTrigger: Subject<void> = new Subject<void>();

  ngOnInit(): void {
    this.getHomeComponentTrigger();
  }

  getHomeComponentTrigger() {
    return this.homeComponentTrigger.next();
  }

}
