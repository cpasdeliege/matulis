import {inject} from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationService } from "../services/authentication.service";

export const authenticationGuard = () => {
	const authenticationService = inject(AuthenticationService);
	const router = inject(Router);
	return authenticationService.isAuthenticated() ? true : router.parseUrl('/login');
};