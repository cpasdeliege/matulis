import { Component } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';

import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {
	credentials = {username: '', password: ''};
	loginSuccess = null;
	loginError = null;
	error = false;

	constructor(private authenticationService: AuthenticationService, private router: Router) {
		if(this.authenticationService.getToken()){
			//this.router.navigateByUrl('/');
		}
	}
  
	login() {
		this.authenticationService.authenticate(this.credentials.username,this.credentials.password).subscribe(
			(data) => {
				console.log(data)
				this.loginSuccess = data;
				this.authenticationService.saveToken(data.token);
				//this.router.navigateByUrl('/');
				this.loginError = null;
			},
			(error)=> {
				this.loginSuccess = null;
				this.loginError = error;
			}
		);
		return false;
	}
}
