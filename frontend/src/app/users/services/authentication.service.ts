import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Authentication } from '../model/Authentication';
import { BehaviorSubject, Observable, switchMap, } from 'rxjs';
import { NavigationService } from 'src/app/shared/services/navigation.service';
import { interval, Subscription } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
	private authentication: Authentication | null = null;
	private authenticationSubject: BehaviorSubject<Authentication | null> = new BehaviorSubject<Authentication | null>(null);
	private intervalSubscription: Subscription = new Subscription();
	public checkAuthenticationSubscription: Observable<any>;

	constructor(
		private http: HttpClient, 
		private navigationService: NavigationService
	) {
		this.checkAuthenticationSubscription = this.checkAuthentication();
	}

	initAuthentication(authentication:Authentication | null) {
		this.setAuthentication(authentication);
		this.startCheckAuthenticationInterval();
	}

	getAuthentication() {
		return this.authentication;
	}

	getAuthenticationSubject() {
		return this.authenticationSubject;
	}

	setAuthentication(authentication:Authentication | null) {
		this.authentication = authentication;
		this.authenticationSubject.next(this.authentication);
	}

	authenticate(username: string, password: string) {
		return this.http.post<any>(`${environment.apiUrl}/api/authentication/authenticate`, { username: username, password: password });
	}

	logout(redirect:boolean = true) {
		this.http.delete<any>(`${environment.apiUrl}/api/authentication/logout`).subscribe(
			(deleted) => {
				if(deleted) {
					this.setAuthentication(null);
					this.stopCheckAuthenticationInterval();
					if(redirect) this.navigationService.redirect('/login');
				}
			}
		);
	}

	isAuthenticated(){
		return this.authentication != null;
	}

	checkAuthentication() {
		this.checkAuthenticationSubscription = this.http.get<any>(`${environment.apiUrl}/api/authentication/check-token`);
		return this.checkAuthenticationSubscription;
	}

	startCheckAuthenticationInterval() {
		this.intervalSubscription = interval(5 * 60000) // 5min
		.pipe(switchMap(() => this.checkAuthentication()))
		.subscribe({
			next:(data) => {
				if(data.user == null) {
					this.logout();
				}
			},
			error:(error) => {
				console.error(error);
				this.logout();
			}
		});
	}
	  
	stopCheckAuthenticationInterval() {
		if (this.intervalSubscription) {
		  	this.intervalSubscription.unsubscribe();
		}
	}
}
