import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/authentication/services/authentication.service';
import { Subscription } from 'rxjs';
import { Authentication } from 'src/app/authentication/model/Authentication';

@Component({
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit, OnDestroy {

	authentication: Authentication | null = null;
	subscriptions:Subscription[] = [];

	constructor(private authenticationService:AuthenticationService) {}

	ngOnInit() {
		let authSub = this.authenticationService.getAuthenticationSubject();
		if(authSub) {
			this.subscriptions.push(authSub.subscribe({
				next: (data) => {
					console.log("AUTHENTICATION CHANGED :");
					console.log(data);
				}
			}));
		}
	}

	ngOnDestroy() {
		for (const subscription of this.subscriptions) {
			subscription.unsubscribe();
		}
	}

}