import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProjectOverviewModule} from "./project-overview.module";

const routes: Routes = [
  {path: 'project-overview', component: ProjectOverviewModule},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProjectOverviewRoutingModule {
}
