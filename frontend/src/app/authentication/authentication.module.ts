import { NgModule } from '@angular/core';

import { SharedModule } from '../shared/shared.module';
import { AuthenticationRoutingModule } from './authentication-routing.module';

import { LoginComponent } from './components/login.component';

import { AuthenticationService } from './services/authentication.service';

@NgModule({
	declarations: [
		LoginComponent
	],
	imports: [
		SharedModule,
		AuthenticationRoutingModule
	],
	providers: [AuthenticationService],
})
export class AuthenticationModule { }
