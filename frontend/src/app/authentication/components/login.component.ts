import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';

import { Authentication } from '../model/Authentication';

import { plainToClass } from 'class-transformer';
import { NavigationService } from 'src/app/shared/services/navigation.service';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
	
	private unsubscribe$ = new Subject<void>();

	credentials = {username: '', password: ''};
	loginSuccess = null;
	loginError = null;
	error = false;

	constructor(
		private authenticationService: AuthenticationService, 
		private navigationService: NavigationService
	) {}
  
	ngOnInit() {
		if (this.authenticationService.isAuthenticated()){this.navigationService.redirectToPrevious()};
	}

	login() {
		this.authenticationService.authenticate(this.credentials.username,this.credentials.password)
		.pipe(takeUntil(this.unsubscribe$))
		.subscribe({
			next: (data) => {
				this.loginError = null;
				this.loginSuccess = data;
				this.authenticationService.initAuthentication(plainToClass(Authentication, data));
				this.navigationService.redirectToPrevious();
			},
			error: (error)=> {
				this.loginSuccess = null;
				this.loginError = error;
			}
		});
		return false;
	}
}
