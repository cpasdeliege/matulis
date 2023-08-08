import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Authentication } from '../model/Authentication';
import { BehaviorSubject } from 'rxjs';
import { instanceToPlain, plainToInstance } from 'class-transformer';
import { NavigationService } from 'src/app/shared/services/navigation.service';

const AUTH_LOCAL_STORAGE_NAME = 'authentication';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
	private baseUrl = 'http://cpl-app-27:8080'; //TODO : créer une config
	private authentication: Authentication | null = null;
	private authenticationSubject: BehaviorSubject<Authentication | null> = new BehaviorSubject<Authentication | null>(null);

	constructor(
		private http: HttpClient, 
		private navigationService: NavigationService
	) {
		if (typeof localStorage === 'undefined' || localStorage === null) {
			console.error('localStorage is not available.');
			alert("Local Storage est désactivé. Cette application en a besoin pour fonctionner.")
		}
	}

	/* AUTHENTICATION */

	authenticate(username: string, password: string) {
		return this.http.post<any>(`${this.baseUrl}/api/authentication/authenticate`, { username: username, password: password });
	}

	logout(redirect:boolean = true) {
		// TODO : envoyer une requête au webservice pour invalider le token 
		this.setAuthentication(null);
		this.removeLocalAuthentication();
		if(redirect) this.navigationService.redirect('/login');
	}

	isAuthenticated(){
		return this.authentication != null;
	}

	initAuthentication(authentication:Authentication | null, saveLocal:boolean = true) {
		this.setAuthentication(authentication);
		if(saveLocal){this.saveLocalAuthentication()};
	}

	// TODO
	// Fonction à lancer tous les X temps, qui check le webservice pour savoir si le token est tjrs valide
	checkAuthentication(jwtToken:string) {
		return this.http.get<any>(`${this.baseUrl}/api/authentication/check-token`, { params: { token:jwtToken } });
	}

	getAuthentication() {
		return this.authentication;
	}

	setAuthentication(authentication:Authentication | null) {
		this.authentication = authentication;
		this.authenticationSubject.next(this.authentication);
	}

	getAuthenticationSubject() {
		return this.authenticationSubject;
	}

	/* LOCAL STORAGE */

	saveLocalAuthentication() {
		localStorage.setItem(AUTH_LOCAL_STORAGE_NAME, JSON.stringify(instanceToPlain(this.authentication)));
	}

	getLocalAuthentication() {
		let localAuthentication = localStorage.getItem(AUTH_LOCAL_STORAGE_NAME);
		return localAuthentication != null ? plainToInstance(Authentication, JSON.parse(localAuthentication)) : null;
	}

	removeLocalAuthentication() {
		localStorage.removeItem(AUTH_LOCAL_STORAGE_NAME);
	}
}
