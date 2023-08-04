import { Component, OnInit } from '@angular/core';
import { CoreService } from '../services/core.service';
import { HttpClient } from '@angular/common/http';
import { AuthenticationService } from 'src/app/authentication/services/authentication.service';

@Component({
  templateUrl: './home.component.html'
})
export class HomeComponent {

	constructor(private authenticationService:AuthenticationService) {
		this.authenticationService.getAuthenticationSubject().subscribe({
			next: (data) => {
				console.log("AUTHENTICATION CHANGED :");
				console.log(data);
			}
		});
	}

}