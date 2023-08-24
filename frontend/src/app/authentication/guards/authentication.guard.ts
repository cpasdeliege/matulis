import { inject } from '@angular/core';

import { AuthenticationService } from "../services/authentication.service";
import { NavigationService } from 'src/app/shared/services/navigation.service';
import { catchError, map, of } from 'rxjs';
import { plainToClass } from 'class-transformer';
import { Authentication } from '../model/Authentication';

export const authenticationGuard = () => {
	const authenticationService = inject(AuthenticationService);
	const navigationService = inject(NavigationService);
	
	return authenticationService.isAuthenticated() ? true : authenticationService.checkAuthentication()
	.pipe(
		map((data) => {
			if(data.user != null) {
				authenticationService.initAuthentication(plainToClass(Authentication, data));
				return of(true);
			} 
			return of(navigationService.parseUrl("/login"));
		}),
		catchError((error) => {
			console.error(error);
			return of(navigationService.parseUrl("/login"));
		})
	);
}