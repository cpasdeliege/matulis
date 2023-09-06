import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
	constructor(
		private http: HttpClient, 
	) {}

	search(username: string) {
		return this.http.get<any>(`${environment.apiUrl}/api/search-users`, { params: { username: username } });
	}

	update(user:any) {
		return this.http.put<any>(`${environment.apiUrl}/api/user/update`, user);
	}
}
