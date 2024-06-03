import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../core/services/authentication.service";
import {SectionService} from "../../core/services/section.service";

@Component({
  selector: 'app-dashboard-dode',
  templateUrl: './dashboard-dode.component.html',
  styleUrls: ['./dashboard-dode.component.scss']
})
export class DashboardDodeComponent implements OnInit {

  selectedSection: string | null = null;
  loggedInUsername: string | null = null;

  constructor(
    public authService: AuthenticationService,
    private sectionService: SectionService
  ) { }

  ngOnInit(): void {
    this.loggedInUsername = this.authService.getLoggedInUsername();
    this.sectionService.selectedSection$.subscribe(section => {
      this.selectedSection = section;
    });
  }

}
