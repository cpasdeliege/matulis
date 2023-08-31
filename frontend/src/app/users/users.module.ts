import { NgModule } from '@angular/core';

import { SharedModule } from '../shared/shared.module';
import { UsersRoutingModule } from './users-routing.module';

import { LoginComponent } from './components/login.component';

@NgModule({
	declarations: [
		LoginComponent
	],
	imports: [
		SharedModule,
		UsersRoutingModule
	],
})
export class UsersModule { }
