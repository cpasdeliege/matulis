import { inject } from '@angular/core';

import { AuthenticationService } from "../services/authentication.service";
import { NavigationService } from 'src/app/shared/services/navigation.service';

export const authenticationGuard = () => {
	const authenticationService = inject(AuthenticationService);
	const navigationService = inject(NavigationService);
	
	return authenticationService.isAuthenticated() ? true : navigationService.parseUrl('/login');
}