import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { DeveloperResponse } from "../../core/models/developer.model";
import { PremiseResponse } from "../../core/models/premise.model";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { PremiseService } from "../../core/services/premise.service";
import { DeveloperService } from "../../core/services/developer.service";
import { MatPaginator } from "@angular/material/paginator";
import { MatDialog } from '@angular/material/dialog';
import { DeleteConfirmationDialogComponent } from "../delete-confirmation-dialog/delete-confirmation-dialog.component"; // Import MatDialog

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
  pagedPremises: PremiseResponse[] = [];
  selectedPremise: PremiseResponse | null = null;
  premiseForm: FormGroup;
  isNewPremise: boolean = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  currentPage: number = 0;
  pageSize: number = 5;

  constructor(
    private fb: FormBuilder,
    private premiseService: PremiseService,
    private developerService: DeveloperService,
    private dialog: MatDialog
  ) {
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
        this.updatePagedPremises();
      });
    }
  }

  onPageChange(event: any): void {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.updatePagedPremises();
  }

  updatePagedPremises(): void {
    const startIndex = this.currentPage * this.pageSize;
    this.pagedPremises = this.premises.slice(startIndex, startIndex + this.pageSize);
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

  onSubmit(): void {
    if (this.premiseForm.valid) {
      if (this.isNewPremise) {
        this.saveNewPremise();
      } else {
        this.updatePremise();
      }
    }
  }

  saveNewPremise(): void {
    const newPremise = this.premiseForm.value;
    this.premiseService.createPremise(newPremise).subscribe(() => {
      this.fetchPremises();
      this.isNewPremise = false;
      this.premiseForm.reset();
    });
  }

  updatePremise(): void {
    const updatedPremise = this.premiseForm.value;
    this.premiseService.updatePremise(updatedPremise.id, updatedPremise).subscribe(() => {
      this.fetchPremises();
      this.isNewPremise = false;
      this.selectedPremise = null;
    });
  }

  cancelEdit(): void {
    this.selectedPremise = null;
    this.isNewPremise = false;
    this.premiseForm.reset();
  }

  deletePremise(premise: PremiseResponse): void {
    const dialogRef = this.dialog.open(DeleteConfirmationDialogComponent, {
      width: '250px',
      data: { title: 'Delete Premise', message: 'Do you really want to delete this premise? This action cannot be undone.' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.premiseService.deletePremise(premise.id.toString()).subscribe(() => {
          this.fetchPremises();
        });
      }
    });
  }
}
