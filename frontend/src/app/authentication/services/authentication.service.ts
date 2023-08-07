import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Authentication } from '../model/Authentication';
import { BehaviorSubject } from 'rxjs';
import { instanceToPlain, plainToClass } from 'class-transformer';
import { NavigationService } from 'src/app/shared/services/navigation.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
	private baseUrl = 'http://cpl-app-27:8080'; //TODO : créer une config
	private authentication: Authentication | null = null;
	private authenticationSubject: BehaviorSubject<Authentication> | null = null;

	constructor(
		private http: HttpClient, 
		private navigationService: NavigationService
	) {}

	init() {
		this.initCheckLocalAuthentication();
	}

	authenticate(username: string, password: string) {
		return this.http.post<any>(`${this.baseUrl}/api/authentication/authenticate`, { username: username, password: password });
	}

	logout() {
		// TODO : envoyer une requête au webservice pour invalider le token 
		this.setAuthentication(null);
		this.removeLocalAuthentication();
		this.navigationService.redirect('/login');
	}

	isAuthenticated(){
		return this.authentication != null;
	}

	initAuthentication(authentication:Authentication, saveLocal:boolean = true) {
		this.setAuthentication(authentication);
		this.setAuthenticationSubject();
		if(saveLocal){this.saveLocalAuthentication()};
	}

	initCheckLocalAuthentication() {
		let localAuthentication = this.getLocalAuthentication();
		if (localAuthentication != null) {
			this.initAuthentication(localAuthentication,false);
		}
	}

	checkAuthentication() {
		// TODO
		// Fonction à lancer tous les X temps, qui check le webservice pour savoir si le token est tjrs valide
	}

	getAuthentication() {
		return this.authentication;
	}

	setAuthentication(authentication:Authentication | null) {
		this.authentication = authentication;
	}

	getAuthenticationSubject() {
		return this.authenticationSubject;
	}

	setAuthenticationSubject() {
		this.authenticationSubject = this.authentication != null ? new BehaviorSubject<Authentication>(this.authentication) : null;
	}

	saveLocalAuthentication() {
		localStorage.setItem('matulisAuthentication', JSON.stringify(instanceToPlain(this.authentication)));
	}

	getLocalAuthentication() {
		let localAuthentication = localStorage.getItem('matulisAuthentication');
		return localAuthentication != null ? plainToClass(Authentication,localAuthentication) : null;
	}

	removeLocalAuthentication() {
		localStorage.removeItem('matulisAuthentication');
	}
}
