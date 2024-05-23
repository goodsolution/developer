import {Component, OnInit} from '@angular/core';
import {DeveloperResponse} from "../../core/models/developer.model";
import {DeveloperService} from "../../core/services/developer.service";
import {SearchResultDeveloperModel} from "../../core/models/searchResultDeveloper.model";
import {AuthenticationService} from "../../core/services/authentication.service";

@Component({
  selector: 'app-dashboard-dode',
  templateUrl: './dashboard-dode.component.html',
  styleUrls: ['./dashboard-dode.component.scss']
})
export class DashboardDodeComponent implements OnInit {

  developers: DeveloperResponse[] = [];
  selectedDeveloper: DeveloperResponse | null = null;
  showDeveloperSection: boolean = false;
  selectedSection: string = 'developer';
  loggedInUsername: string | null = null;

  constructor(
    private developerService: DeveloperService,
    public authService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.fetchDevelopers();
    this.loggedInUsername = this.authService.getLoggedInUsername();
  }

  selectSection(section: string): void {
    this.selectedSection = section;
  }

  fetchDevelopers(): void {
    this.developerService.fetchDevelopers().subscribe((response: SearchResultDeveloperModel) => {
      this.developers = response.developers;
    });
  }

  selectDeveloper(developer: DeveloperResponse): void {
    this.selectedDeveloper = developer;
  }

  toggleDeveloperSection(): void {
    this.showDeveloperSection = !this.showDeveloperSection;
  }

  handleKey(event: KeyboardEvent): void {
    if (event.key === 'Enter' || event.key === ' ') {
      this.toggleDeveloperSection();
    }
  }

  onDeveloperKeydown(event: KeyboardEvent, developer: DeveloperResponse): void {
    if (event.key === 'Enter' || event.key === ' ') {
      this.selectDeveloper(developer);
    }
  }


  editDeveloper(): void {
    // Implement edit functionality here
  }

  saveDeveloper(): void {
    // Implement save functionality here
  }

  cancelEdit(): void {
    this.selectedDeveloper = null;
  }

}
