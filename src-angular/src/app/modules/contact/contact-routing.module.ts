import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ContactDodeComponent} from "./contact-dode/contact-dode.component";
import {ContactAntalComponent} from "./contact-antal/contact-antal.component";

const routes: Routes = [
  {path: 'contact/domdevelopment', component: ContactDodeComponent},
  {path: 'contact/antal', component: ContactAntalComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ContactRoutingModule { }
