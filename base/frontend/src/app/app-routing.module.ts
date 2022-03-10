import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AppComponent} from "./app.component";
import {SettingOverviewComponent} from "./components/setting-overview/setting-overview.component";
import {DomainOverviewScreenComponent} from "./components/domain-overview-screen/domain-overview-screen.component";
import {DomainDetailScreenComponent} from "./components/domain-detail-screen/domain-detail-screen.component";

const routes: Routes = [
  {path: 'home', component: DomainOverviewScreenComponent},
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'settings', component: SettingOverviewComponent},
  {path: 'details/:id', component: DomainDetailScreenComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
