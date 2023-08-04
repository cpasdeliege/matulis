import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { SharedModule } from '../shared/shared.module';
import { AppRoutingModule } from './core-routing.module';
import { AuthenticationModule } from '../authentication/authentication.module';

import { CoreComponent } from './components/core.component';

@NgModule({
	declarations: [
		CoreComponent
	],
	imports: [
		SharedModule,
		BrowserModule,
		AppRoutingModule,
		HttpClientModule,
		AuthenticationModule
	],
	providers: [],
	bootstrap: [CoreComponent]
})
export class CoreModule { }
