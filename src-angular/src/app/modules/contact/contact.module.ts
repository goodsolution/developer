import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ContactRoutingModule} from './contact-routing.module';
import {ContactDodeComponent} from './contact-dode/contact-dode.component';
import {ContactAntalComponent} from './contact-antal/contact-antal.component';
import {SharedModule} from "../shared/shared.module";


@NgModule({
  declarations: [
    ContactDodeComponent,
    ContactAntalComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    ContactRoutingModule
  ],
  exports: [
    ContactDodeComponent,
    ContactAntalComponent,
  ]
})
export class ContactModule { }
