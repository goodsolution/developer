import {Component, OnInit} from '@angular/core';
import {DeveloperResponse} from "../../core/models/developer.model";
import {DeveloperService} from "../../core/services/developer.service";
import {SearchResultDeveloperModel} from "../../core/models/searchResultDeveloper.model";

@Component({
  selector: 'app-dashboard-antal',
  templateUrl: './dashboard-antal.component.html',
  styleUrls: ['./dashboard-antal.component.scss']
})
export class DashboardAntalComponent implements OnInit {
  developers: DeveloperResponse[] = [];
  selectedDeveloper: DeveloperResponse | null = null;
  showDeveloperSection: boolean = false;

  constructor(private developerService: DeveloperService) {
  }

  ngOnInit(): void {
    this.fetchDevelopers();
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
