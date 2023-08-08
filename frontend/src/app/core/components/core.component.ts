import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, Subscription, takeUntil } from 'rxjs';
import { Authentication } from 'src/app/authentication/model/Authentication';
import { AuthenticationService } from 'src/app/authentication/services/authentication.service';
import { NavigationService } from 'src/app/shared/services/navigation.service';
import { ToolService } from 'src/app/shared/services/tool.service';

@Component({
  selector: 'core-root',
  templateUrl: './core.component.html',
})
export class CoreComponent implements OnInit, OnDestroy {

	private unsubscribe$ = new Subject<void>();

	authentication:Authentication | null = null;

	constructor(
		private authenticationService:AuthenticationService,
		private toolService:ToolService,
		private navigationService:NavigationService
	){}

	ngOnInit() {
		// INIT APP START
		this.navigationService.init();

		// AUTHENTICATION

		// On met à jour l'authentication dès qu'il y a un changement
		this.authentication = this.authenticationService.getAuthentication();
		this.authenticationService.getAuthenticationSubject()
		.pipe(takeUntil(this.unsubscribe$))
		.subscribe({
			next: (data) => {
				this.authentication = this.authenticationService.getAuthentication();
			}
		});
		
	}

	ngOnDestroy() {
		this.toolService.unsubscribe(this.unsubscribe$);
	}

	logout() {
		this.authenticationService.logout();
	}
}
