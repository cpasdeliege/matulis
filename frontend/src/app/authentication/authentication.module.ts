import { NgModule } from '@angular/core';

import { SharedModule } from '../shared/shared.module';
import { AuthenticationRoutingModule } from './authentication-routing.module';

import { LoginComponent } from './components/login.component';

@NgModule({
	declarations: [
		LoginComponent
	],
	imports: [
		SharedModule,
		AuthenticationRoutingModule
	],
	providers: [ ],
})
export class AuthenticationModule { }
