import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {
	private baseUrl = 'http://cpl-app-27:8080'; //TODO : créer une config

	constructor(
		private http: HttpClient, 
	) {}

	search(username: string) {
		return this.http.get<any>(`${this.baseUrl}/api/search-users`, { params: { username: username } });
	}

	update(user:any) {
		return this.http.put<any>(`${this.baseUrl}/api/user/update`, user);
	}
}
