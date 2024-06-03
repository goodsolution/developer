import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SectionService {

  private selectedSectionSubject = new BehaviorSubject<string>(''); // default section can be set here
  selectedSection$ = this.selectedSectionSubject.asObservable();

  constructor() {
    // Optionally, load the initial section from local storage or a default value
    const initialSection = localStorage.getItem('selectedSection') || 'defaultSection';
    this.selectedSectionSubject.next(initialSection);
  }

  selectSection(section: string): void {
    this.selectedSectionSubject.next(section);
    localStorage.setItem('selectedSection', section); // Save the user's selection
  }

}
