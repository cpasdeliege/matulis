import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AuthenticationRoutingModule } from './authentication-routing.module';

import { LoginComponent } from './components/login.component';

import { AuthenticationService } from './services/authentication.service';

@NgModule({
  declarations: [
	LoginComponent
  ],
  imports: [
    CommonModule,
	AuthenticationRoutingModule,
	FormsModule
  ],
  providers: [AuthenticationService],
})
export class AuthenticationModule { }
