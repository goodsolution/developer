import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MaterialModule} from "./material/material.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {DefaultComponent} from './default/default.component';
import {FiltersModule} from "./filters/filters.module";


@NgModule({
  declarations: [
    DefaultComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    CommonModule, MaterialModule, FormsModule, ReactiveFormsModule, BrowserAnimationsModule, DefaultComponent, FiltersModule
  ]
})
export class SharedModule {
}
