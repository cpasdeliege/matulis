import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, Subscription, takeUntil } from 'rxjs';
import { AuthenticationService } from 'src/app/authentication/services/authentication.service';
import { NavigationService } from 'src/app/shared/services/navigation.service';
import { ToolService } from 'src/app/shared/services/tool.service';

@Component({
  selector: 'core-root',
  templateUrl: './core.component.html',
})
export class CoreComponent implements OnInit, OnDestroy {

	private unsubscribe$ = new Subject<void>();

	constructor(
		private authenticationService:AuthenticationService,
		private toolService:ToolService,
		private navigationService:NavigationService
	){}

	ngOnInit() {
		// INIT APP START
		this.navigationService.init();
		this.authenticationService.init();

		// AUTH
		let authSub = this.authenticationService.getAuthenticationSubject();
		if(authSub) {
			authSub
			.pipe(takeUntil(this.unsubscribe$))
			.subscribe({
				next: (data) => {
					console.log("AUTHENTICATION CHANGED :");
					console.log(data);
				}
			});
		}
	}

	ngOnDestroy() {
		this.toolService.unsubscribe(this.unsubscribe$);
	}
}
