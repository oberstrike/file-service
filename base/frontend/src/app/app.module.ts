import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DeletionModalComponent } from './components/deletion-modal/deletion-modal.component';
import {DynamicIoModule, DynamicModule} from "ng-dynamic-component";

@NgModule({
  declarations: [
    AppComponent,
    DeletionModalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    DynamicModule,
    DynamicIoModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
