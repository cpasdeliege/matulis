import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { SharedModule } from '../shared/shared.module';
import { AppRoutingModule } from './core-routing.module';
import { UsersModule } from '../users/users.module';

import { CoreComponent } from './components/core.component';
import { TestComponent } from './components/test.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HomeComponent } from './components/home.component';

@NgModule({
	declarations: [
		CoreComponent,
		TestComponent,
		HomeComponent,
	],
	imports: [
		SharedModule,
		BrowserModule,
		AppRoutingModule,
		HttpClientModule,
		UsersModule,
  		NgbModule
	],
	bootstrap: [CoreComponent]
})
export class CoreModule { }
