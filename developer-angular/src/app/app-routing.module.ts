import {RouterModule, Routes} from "@angular/router";
import {PremisesComponent} from "./premises/premises.component";
import {NgModule} from "@angular/core";
import {PremiseDetailComponent} from "./premises/premise-detail/premise-detail.component";
import {NavbarContactComponent} from "./navbar-developer/navbar-contact/navbar-contact.component";

const routes: Routes = [
  {path: 'premises', component: PremisesComponent},
  {path: 'premises/:id', component: PremiseDetailComponent},
  {path: 'developer/contact', component: NavbarContactComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
