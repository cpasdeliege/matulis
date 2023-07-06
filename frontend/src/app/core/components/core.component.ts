import { Component } from '@angular/core';
import { CoreService } from '../services/core.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

import { finalize } from 'rxjs/operators';

@Component({
  selector: 'core-root',
  templateUrl: './core.component.html',
})
export class CoreComponent {
  title = 'matulis';

  constructor(private coreService: CoreService, private http: HttpClient, private router: Router) {
	this.coreService.authenticate(undefined, undefined);
  }

  logout() {
	this.http.post('logout', {}).pipe(
		finalize(() => {
			this.coreService.authenticated = false;
			this.router.navigateByUrl('/login');
		})
	).subscribe();
  }

}
