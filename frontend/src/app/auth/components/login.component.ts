import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { AppService } from '../../core/services/core.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  template: `
    <h2>Connexion</h2>
    <div class="alert alert-danger" [hidden]="!error">
		There was a problem logging in. Please try again.
	</div>
	<form role="form" (submit)="login()">
		<div class="form-group">
			<label for="username">Username:</label> <input type="text"
				class="form-control" id="username" name="username" [(ngModel)]="credentials.username"/>
		</div>
		<div class="form-group">
			<label for="password">Password:</label> <input type="password"
				class="form-control" id="password" name="password" [(ngModel)]="credentials.password"/>
		</div>
		<button type="submit" class="btn btn-primary">Submit</button>
	</form>
  `
})
export class LoginComponent {
	credentials = {username: '', password: ''};
	error = false;

	constructor(private app: AppService, private http: HttpClient, private router: Router) {
	}
  
	login() {
		this.app.authenticate(this.credentials, () => {
			this.router.navigateByUrl('/');
		});
		return false;
	}
}
