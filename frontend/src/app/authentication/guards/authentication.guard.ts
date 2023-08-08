import { inject } from '@angular/core';

import { AuthenticationService } from "../services/authentication.service";
import { NavigationService } from 'src/app/shared/services/navigation.service';

export const authenticationGuard = () => {
	const authenticationService = inject(AuthenticationService);
	const navigationService = inject(NavigationService);

	if(!authenticationService.isAuthenticated()){
		// Pas authentifié mais on vérifie si le token du cookie est bon via l'API
		return authenticationService.checkAuthentication();
	} else {
		// Utilisateur authentifié et localStorage présent
		return true;
	}

}