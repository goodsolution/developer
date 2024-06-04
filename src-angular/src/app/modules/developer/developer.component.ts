import {Component, OnInit} from '@angular/core';
import {DeveloperResponse} from "../core/models/developer.model";
import {DeveloperService} from "../core/services/developer.service";
import {SearchResultDeveloperModel} from "../core/models/searchResultDeveloper.model";
import {CityService} from "../core/services/city.service";
import {CityResponse} from "../core/models/city.model";
import {SearchResultCityModel} from "../core/models/searchResultCity.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-developer',
  templateUrl: './developer.component.html',
  styleUrls: ['./developer.component.scss']
})
export class DeveloperComponent implements OnInit {

  developers: DeveloperResponse[] = [];
  selectedDeveloper: DeveloperResponse | null = null;
  cities: CityResponse[] = [];
  developerForm: FormGroup;
  isNewDeveloper: boolean = false;

  constructor(
    private fb: FormBuilder,
    private developerService: DeveloperService,
    private cityService: CityService
  ) {
    this.developerForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      addressCountry: ['', Validators.required],
      addressStreet: ['', Validators.required],
      addressBuildingNumber: ['', Validators.required],
      addressFlatNumber: [''],
      addressPostalCode: ['', Validators.required],
      telephoneNumber: ['', Validators.pattern(/^[0-9]{10}$/)],
      faxNumber: ['', Validators.pattern(/^[0-9]{10}$/)],
      email: ['', [Validators.required, Validators.email]],
      taxIdentificationNumber: ['', Validators.required],
      cityId: ['', Validators.required],
      logoUrl: ['']
    });
  }

  ngOnInit(): void {
    this.fetchDevelopers();
    this.fetchCities();
  }

  fetchDevelopers(): void {
    this.developerService.fetchDevelopers().subscribe((response: SearchResultDeveloperModel) => {
      this.developers = response.developers;
    });
  }

  fetchCities(): void {
    this.cityService.fetchCities().subscribe((response: SearchResultCityModel) => {
      this.cities = response.cities;
    });
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
      logoUrl: ''
    } as DeveloperResponse;
    this.isNewDeveloper = true;
    this.developerForm.reset();
  }

  editDeveloper(developer: DeveloperResponse): void {
    this.selectedDeveloper = { ...developer };
    this.isNewDeveloper = false;
    this.developerForm.patchValue(developer);
  }

  saveNewDeveloper(): void {
    if (this.developerForm.valid) {
      const newDeveloper = this.developerForm.value;
      this.developerService.addDeveloper(newDeveloper).subscribe(() => {
        this.fetchDevelopers();
        this.selectedDeveloper = null;
        this.isNewDeveloper = false;
      });
    }
  }

  updateDeveloper(): void {
    if (this.developerForm.valid) {
      const updatedDeveloper = this.developerForm.value;
      this.developerService.updateDeveloper(updatedDeveloper).subscribe(() => {
        this.fetchDevelopers();
        this.selectedDeveloper = null;
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
      this.fetchDevelopers();
    });
  }

}
