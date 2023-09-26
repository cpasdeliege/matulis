import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { Authentication } from 'src/app/users/model/Authentication';
import { AuthenticationService } from 'src/app/users/services/authentication.service';
import { NavigationService } from 'src/app/shared/services/navigation.service';
import { ToolService } from 'src/app/shared/services/tool.service';
import { Title } from '@angular/platform-browser';

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
		private navigationService:NavigationService,
		private title:Title
	){}

	ngOnInit() {
		// INIT APP START
		this.navigationService.init();

		// Meta title du site
		this.title.setTitle("MATULIS - CPAS de Liège");

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
