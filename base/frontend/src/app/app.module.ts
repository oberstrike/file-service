import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DeletionModalComponent } from './components/deletion-modal/deletion-modal.component';
import {DynamicIoModule, DynamicModule} from "ng-dynamic-component";
import { DomainDetailScreenComponent } from './components/domain-detail-screen/domain-detail-screen.component';
import { DomainOverviewScreenComponent } from './components/domain-overview-screen/domain-overview-screen.component';
import { SettingOverviewComponent } from './components/setting-overview/setting-overview.component';
import { DomainCreateComponent } from './components/domain-create/domain-create.component';
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    DeletionModalComponent,
    DomainDetailScreenComponent,
    DomainOverviewScreenComponent,
    SettingOverviewComponent,
    DomainCreateComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    DynamicModule,
    DynamicIoModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
