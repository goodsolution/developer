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
  isNewDeveloper: boolean = false;

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

  addNewDeveloper(): void {
    this.selectedDeveloper = {
      id: 0,
      name: '',
      addressCountry: '',
      addressStreet: '',
      addressBuildingNumber: '',
      addressFlatNumber: '',
      addressPostalCode: '',
      telephoneNumber: '',
      faxNumber: '',
      email: '',
      taxIdentificationNumber: '',
      cityId: 0,
      logoUrl: '',
      code: '',
      createdAt: ''
    } as DeveloperResponse;
    this.isNewDeveloper = true;
  }

  editDeveloper(developer: DeveloperResponse): void {
    this.selectedDeveloper = { ...developer }; // Create a copy of the developer object
    this.isNewDeveloper = false;
  }

  saveNewDeveloper(): void {
    if (this.selectedDeveloper) {
      this.developerService.addDeveloper(this.selectedDeveloper).subscribe(() => {
        this.fetchDevelopers(); // Refresh the list
        this.selectedDeveloper = null; // Deselect the developer
        this.isNewDeveloper = false;
      });
    }
  }

  updateDeveloper(): void {
    if (this.selectedDeveloper) {
      this.developerService.updateDeveloper(this.selectedDeveloper).subscribe(() => {
        this.fetchDevelopers(); // Refresh the list
        this.selectedDeveloper = null; // Deselect the developer
        this.isNewDeveloper = false;
      });
    }
  }

  cancelEdit(): void {
    this.selectedDeveloper = null;
    this.isNewDeveloper = false;
  }

  deleteDeveloper(developer: DeveloperResponse): void {
    this.developerService.deleteDeveloper(developer.id).subscribe(() => {
      this.fetchDevelopers(); // Refresh the list
    });
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

}
