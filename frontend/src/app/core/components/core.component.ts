import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/authentication/services/authentication.service';

@Component({
  selector: 'core-root',
  templateUrl: './core.component.html',
})
export class CoreComponent implements OnInit {

	constructor(private authenticationService:AuthenticationService){}

	ngOnInit(): void {
		this.authenticationService.initCheckLocalAuthentication();
	}
}
