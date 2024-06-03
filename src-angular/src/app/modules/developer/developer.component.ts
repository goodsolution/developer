import {Component, OnInit} from '@angular/core';
import {DeveloperResponse} from "../core/models/developer.model";
import {DeveloperService} from "../core/services/developer.service";
import {SearchResultDeveloperModel} from "../core/models/searchResultDeveloper.model";

@Component({
  selector: 'app-developer',
  templateUrl: './developer.component.html',
  styleUrls: ['./developer.component.scss']
})
export class DeveloperComponent implements OnInit {

  developers: DeveloperResponse[] = [];
  selectedDeveloper: DeveloperResponse | null = null;
  isNewDeveloper: boolean = false;

  constructor(private developerService: DeveloperService) { }

  ngOnInit(): void {
    this.fetchDevelopers();
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

}
