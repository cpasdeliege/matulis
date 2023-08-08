import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Authentication } from '../model/Authentication';
import { BehaviorSubject, take } from 'rxjs';
import { plainToClass } from 'class-transformer';
import { NavigationService } from 'src/app/shared/services/navigation.service';

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

	authenticate(username: string, password: string) {
		return this.http.post<any>(`${this.baseUrl}/api/authentication/authenticate`, { username: username, password: password });
	}

	logout(redirect:boolean = true) {
		// TODO : envoyer une requête au webservice pour invalider le token 
		this.setAuthentication(null);
		if(redirect) this.navigationService.redirect('/login');
	}

	isAuthenticated(){
		return this.authentication != null;
	}

	// TODO
	// Fonction à lancer tous les X temps, qui check le webservice pour savoir si le token est tjrs valide
	checkAuthentication() {
		return this.http.get<any>(`${this.baseUrl}/api/authentication/check-token`)
		.pipe(take(1))
		.subscribe({
			next: (data:any) => {
				// Si le token est valide et le user est bien présent
				if (data.user != null) {
					// on peut authentifier l'utilisateur sur base de ce token
					this.setAuthentication(plainToClass(Authentication, data));
				} else {
					// sinon redirection vers /login
					this.navigationService.redirect('/login');
				}	
			},
			error: (error) => {
				// Problème au niveau de l'API
				console.error(error);
				this.navigationService.redirect('/login');
			}
		});
	}
}
