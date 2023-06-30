import { Component } from '@angular/core';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-login',
  template: `
    <h2>Connexion</h2>
    <form (submit)="login()">
		<label for="username">Nom d'utilisateur:</label>
		<input type="text" id="username" [(ngModel)]="username" name="username" required>
		<br><br>
		<label for="password">Mot de passe:</label>
		<input type="password" id="password" [(ngModel)]="password" name="password" required>
		<br><br>
		<button type="submit">Se connecter</button>
    </form>
  `
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private authService: AuthService) {}

  login() {
    this.authService.login(this.username, this.password).subscribe(
      (response) => {
        // Enregistrement du jeton JWT dans le stockage local
        this.authService.saveToken(response.token);
        // Redirection vers une autre page après la connexion réussie
      },
      (error) => {
        // Gestion des erreurs de connexion
      }
    );
  }
}
