import { Component, Input, OnInit } from '@angular/core';
import { DeveloperResponse } from "../../core/models/developer.model";
import { PremiseResponse } from "../../core/models/premise.model";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { PremiseService } from "../../core/services/premise.service";
import { DeveloperService } from "../../core/services/developer.service";

@Component({
  selector: 'app-premise',
  templateUrl: './premise.component.html',
  styleUrls: ['./premise.component.scss']
})
export class PremiseComponent implements OnInit {
  @Input() adminView: boolean = false;
  developers: DeveloperResponse[] = [];
  selectedDeveloper: DeveloperResponse | null = null;
  premises: PremiseResponse[] = [];
  selectedPremise: PremiseResponse | null = null;
  premiseForm: FormGroup;
  isNewPremise: boolean = false;

  constructor(
    private fb: FormBuilder,
    private premiseService: PremiseService,
    private developerService: DeveloperService
  ) {
    // Initialize the form group with validation
    this.premiseForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      addressStreet: ['', Validators.required],
      buildingNumber: ['', Validators.required],
      flatNumber: [''],
      postalCode: ['', Validators.required],
      developerId: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.fetchDevelopers();
  }

  fetchDevelopers(): void {
    this.developerService.fetchAllDevelopers().subscribe((response) => {
      this.developers = response.developers;
    });
  }

  selectDeveloper(developer: DeveloperResponse): void {
    this.selectedDeveloper = developer;
    this.fetchPremises();
  }

  fetchPremises(): void {
    if (this.selectedDeveloper) {
      this.premiseService.getPremisesByInvestmentId(this.selectedDeveloper.id).subscribe((response) => {
        this.premises = response.premisesGetResponse;
      });
    }
  }

  addNewPremise(): void {
    this.selectedPremise = null;
    this.isNewPremise = true;
    this.premiseForm.reset();
    if (this.selectedDeveloper) {
      this.premiseForm.controls['developerId'].setValue(this.selectedDeveloper.id);
    }
  }

  editPremise(premise: PremiseResponse): void {
    this.selectedPremise = premise;
    this.isNewPremise = false;
    this.premiseForm.patchValue(premise);
  }

  saveNewPremise(): void {
    if (this.premiseForm.valid) {
      const newPremise = this.premiseForm.value;
      this.premiseService.createPremise(newPremise).subscribe(() => {
        this.fetchPremises();
        this.isNewPremise = false;
        this.premiseForm.reset();
      });
    }
  }

  updatePremise(): void {
    if (this.premiseForm.valid) {
      const updatedPremise = this.premiseForm.value;
      this.premiseService.updatePremise(updatedPremise.id, updatedPremise).subscribe(() => {
        this.fetchPremises();
        this.isNewPremise = false;
        this.selectedPremise = null;
      });
    }
  }

  cancelEdit(): void {
    this.selectedPremise = null;
    this.isNewPremise = false;
  }

  deletePremise(premise: PremiseResponse): void {
    this.premiseService.deletePremise(premise.id.toString()).subscribe(() => {
      this.fetchPremises();
    });
  }
}
