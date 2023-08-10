import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Authentication } from '../model/Authentication';
import { BehaviorSubject, catchError, map, of, switchMap, take, throwError } from 'rxjs';
import { plainToClass } from 'class-transformer';
import { NavigationService } from 'src/app/shared/services/navigation.service';
import { interval, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
	private baseUrl = 'http://cpl-app-27:8080'; //TODO : créer une config
	private authentication: Authentication | null = null;
	private authenticationSubject: BehaviorSubject<Authentication | null> = new BehaviorSubject<Authentication | null>(null);
	private intervalSubscription: Subscription = new Subscription();

	constructor(
		private http: HttpClient, 
		private navigationService: NavigationService
	) {}

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
		return this.http.post<any>(`${this.baseUrl}/api/authentication/authenticate`, { username: username, password: password });
	}

	logout(redirect:boolean = true) {
		// TODO : envoyer une requête au webservice pour invalider le token 
		this.setAuthentication(null);
		this.stopCheckAuthenticationInterval();
		if(redirect) this.navigationService.redirect('/login');
	}

	isAuthenticated(){
		return this.authentication != null;
	}

	checkAuthentication() {
		return this.http.get<any>(`${this.baseUrl}/api/authentication/check-token`);
	}

	startCheckAuthenticationInterval() {
		this.intervalSubscription = interval(1000)
		.pipe(switchMap(() => this.checkAuthentication()))
		.subscribe({
			next:(data) => {
				console.log("check received")
				if(data.user == null) {
					console.log("not auth 1")
					this.logout();
				}
			},
			error:(error) => {
				console.error(error);
				console.log("not auth 2")
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
