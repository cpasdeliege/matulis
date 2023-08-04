import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Authentication } from '../model/Authentication';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
	private baseUrl = 'http://cpl-app-27:8080'; // URL du web service Spring Security
	private authentication!:Authentication;
	private authenticationSubject!: BehaviorSubject<Authentication>;

	constructor(private http: HttpClient) {
		this.setAuthenticationSubject();
	}

	authenticate(username: string, password: string) {
		return this.http.post<any>(`${this.baseUrl}/api/authentication/authenticate`, { username: username, password: password });
	}

	getAuthentication() {
		return this.authentication;
	}

	setAuthentication(authentication:Authentication) {
		this.authentication = authentication;
	}

	getAuthenticationSubject() {
		return this.authenticationSubject;
	}

	setAuthenticationSubject() {
		this.authenticationSubject = new BehaviorSubject<Authentication>(this.authentication);
	}

	saveToken(token: string) {
		localStorage.setItem('tokenMatulis', token);
	}

	getToken() {
		return localStorage.getItem('tokenMatulis');
	}

	removeToken() {
		localStorage.removeItem('tokenMatulis');
	}
}
