import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {InvestmentListComponent} from "./investment-list.component";

const routes: Routes = [
  {path: 'city/:name', component: InvestmentListComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InvestmentListRoutingModule { }
