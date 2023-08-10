import { Injectable } from '@angular/core';
import { Router, NavigationStart, NavigationEnd, ResolveStart } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class NavigationService {
	private previousUrl: string | null = null;
	private currentUrl: string | null = null;

	constructor(private router: Router) {}

	init() {
		this.router.events.subscribe((event) => {
			if (event instanceof ResolveStart) {
				this.previousUrl = this.currentUrl;
				this.currentUrl = event.url;
			}
		});
	}

	redirect(redirectUrl:string) {
		this.router.navigateByUrl(redirectUrl);
	}

	parseUrl(parseUrl:string) {
		return this.router.parseUrl(parseUrl);
	}

	redirectToPrevious() {
		let redirectUrl = this.previousUrl != null ? this.previousUrl : "/";
		this.redirect(redirectUrl);
	}

	getPreviousUrl() {
		return this.previousUrl;
	}
}
