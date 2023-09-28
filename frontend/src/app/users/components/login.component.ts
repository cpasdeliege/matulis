import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';

import { Authentication } from '../model/Authentication';

import { plainToClass } from 'class-transformer';
import { NavigationService } from 'src/app/shared/services/navigation.service';
import { Subject, takeUntil } from 'rxjs';
import { ToolService } from 'src/app/shared/services/tool.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit, OnDestroy {
	
	loading : boolean = false;
	private unsubscribe$ = new Subject<void>();

	credentials = {username: '', password: ''};
	loginError = null;

	constructor(
		private authenticationService: AuthenticationService, 
		private navigationService: NavigationService,
		private toolService:ToolService
	) {}
  
	ngOnInit() {
		if(this.authenticationService.isAuthenticated()){
			this.navigationService.redirect('/home');
		}
	}

	ngOnDestroy() {
		this.toolService.unsubscribe(this.unsubscribe$);
	}

	login() {
		if(!this.loading) {
			this.loading = true;
			this.authenticationService.authenticate(this.credentials.username,this.credentials.password)
			.pipe(takeUntil(this.unsubscribe$))
			.subscribe({
				next: (data) => {
					this.loginError = null;
					this.authenticationService.initAuthentication(plainToClass(Authentication, data));
					this.loading = false;
					this.navigationService.redirectToPrevious();
				},
				error: (data)=> {
					this.loginError = data.error.errorMessage;
					this.loading = false;
				}
			});
		}
	}
}
