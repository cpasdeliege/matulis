import { inject } from '@angular/core';

import { AuthenticationService } from "../services/authentication.service";
import { NavigationService } from 'src/app/shared/services/navigation.service';
import { Authentication } from '../model/Authentication';
import { Subject, takeUntil } from 'rxjs';

export const authenticationGuard = () => {
	const authenticationService = inject(AuthenticationService);
	const navigationService = inject(NavigationService);
	const unsubscribe$ = new Subject<null>();
	
	// Check s'il existe une authentication locale
	let localAuthentication:Authentication | null = authenticationService.getLocalAuthentication();
	if (localAuthentication != null) {
		// Il y a un localStorage, l'utilisateur est-il authentifié ?
		if(!authenticationService.isAuthenticated()){
			// Pas authentifié mais on vérifie si le token du localStorage est bon via l'API
			return authenticationService.checkAuthentication(localAuthentication.getToken())
			.pipe(takeUntil(unsubscribe$))
			.subscribe({
				next: (isValid) => {
					// Si le token local est valide
					if (isValid) {
						// on peut authentifier l'utilisateur sur base de ce token
						authenticationService.initAuthentication(localAuthentication,false);
					} else {
						// Si le token est invalide, on supprime le localStorage
						authenticationService.removeLocalAuthentication();
						// et redirection vers /login
						navigationService.redirect('/login');
					}	
				},
				error: () => {
					// Problème au niveau de l'API
					navigationService.redirect('/login');
				}
			})
		} else {
			// Utilisateur authentifié et localStorage présent
			return true;
		}
	} else {
		// Pas de localStorage mais il est peut être désactivé sur le navigateur de l'utilisateur
		// Vérifier s'il existe une authentification
		return authenticationService.isAuthenticated() ? true : navigationService.redirect('/login');
	}
}