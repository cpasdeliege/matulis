import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';

import { Router } from '@angular/router';
import { Authentication } from '../model/Authentication';

import { plainToClass } from 'class-transformer';
import { NavigationService } from 'src/app/shared/services/navigation.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
	credentials = {username: '', password: ''};
	loginSuccess = null;
	loginError = null;
	error = false;

	constructor(
		private authenticationService: AuthenticationService, 
		private router: Router,
		private navigationService: NavigationService
		) {}
  
	ngOnInit() {
		if (this.authenticationService.isAuthenticated()){this.router.navigateByUrl("/")};
	}

	login() {
		this.authenticationService.authenticate(this.credentials.username,this.credentials.password).subscribe({
			next: (data) => {
				this.loginError = null;
				this.loginSuccess = data;
				this.authenticationService.initAuthentication(plainToClass(Authentication, data));
				//console.log(this.authenticationService.getAuthentication());
				//this.authenticationService.saveToken(data.token);
				let previousUrl = this.navigationService.getPreviousUrl();
				let redirectUrl = previousUrl != null ? previousUrl : "/";
				this.router.navigateByUrl(redirectUrl);
			},
			error: (error)=> {
				this.loginSuccess = null;
				this.loginError = error;
			}
		});
		return false;
	}
}
