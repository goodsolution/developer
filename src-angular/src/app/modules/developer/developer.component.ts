import {Component, OnInit} from '@angular/core';
import {SearchResultDeveloperModel} from "../core/models/searchResultDeveloper.model";
import {DeveloperService} from "../core/services/developer.service";
import {DeveloperResponse} from "../core/models/developer.model";

@Component({
  selector: 'app-developer',
  template: '', // No HTML content, it's a dynamic loader only
  styleUrls: ['./developer.component.scss']
})
export class DeveloperComponent implements OnInit {
  developers: DeveloperResponse[] = [];
  selectedDeveloper: DeveloperResponse | null = null;

  constructor(private developerService: DeveloperService) {
  }

  ngOnInit(): void {
    this.fetchDevelopers();
  }

  fetchDevelopers(): void {
    this.developerService.fetchDevelopers().subscribe((response: SearchResultDeveloperModel) => {
      this.developers = response.developers
    });
  }

  selectDeveloper(developer: DeveloperResponse): void {
    this.selectedDeveloper = developer;
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
