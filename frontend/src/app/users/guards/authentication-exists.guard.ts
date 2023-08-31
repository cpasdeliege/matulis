import { inject } from '@angular/core';

import { AuthenticationService } from "../services/authentication.service";
import { catchError, map, of } from 'rxjs';
import { plainToClass } from 'class-transformer';
import { Authentication } from '../model/Authentication';

export const authenticationExistsGuard = () => {
	const authenticationService = inject(AuthenticationService);
	
	// On vérifie s'il existe une authentification via le cookie httpOnly
	// Mais on ne check pas si l'utilisateur est authentifié, on veut juste init l'authentification si elle existe

	return authenticationService.isAuthenticated() ? true : authenticationService.checkAuthentication()
	.pipe(
		map((data) => {
			if(data.user != null) {
				authenticationService.initAuthentication(plainToClass(Authentication, data));
			} 
			return of(true);
		}),
		catchError((error) => {
			console.error(error);
			return of(true);
		})
	);
}