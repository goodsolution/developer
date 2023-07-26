import {RouterModule, Routes} from "@angular/router";
import {PremisesComponent} from "./premises/premises.component";
import {NgModule} from "@angular/core";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {PremiseDetailComponent} from "./premise-detail/premise-detail.component";
import {NavbarDeveloperCitiesComponent} from "./navbar-developer-cities/navbar-developer-cities.component";

const routes: Routes = [
  {path: 'premises', component: PremisesComponent},
  {path: 'premises/:id', component: PremiseDetailComponent},
  {path: 'cities', component: NavbarDeveloperCitiesComponent},
  {path: 'dashboard', component: DashboardComponent},
  // {path: '', redirectTo: '/dashboard', pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
