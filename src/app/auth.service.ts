import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080'; // URL du web service Spring Security

  constructor(private http: HttpClient) {}

  login(username: string, password: string) {
    return this.http.post<any>(`${this.baseUrl}/login`, { username, password });
  }

  // Méthode pour stocker le jeton JWT dans le stockage local
  saveToken(token: string) {
    localStorage.setItem('jwtToken', token);
  }

  // Méthode pour récupérer le jeton JWT depuis le stockage local
  getToken() {
    return localStorage.getItem('jwtToken');
  }

  // Méthode pour supprimer le jeton JWT du stockage local
  removeToken() {
    localStorage.removeItem('jwtToken');
  }
}
