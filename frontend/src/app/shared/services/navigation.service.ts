import { Injectable } from '@angular/core';
import { Router, NavigationStart, NavigationEnd } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class NavigationService {
  private previousUrl: string = '';
  private currentUrl: string = '';

  constructor(private router: Router) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        this.previousUrl = this.currentUrl;
        this.currentUrl = event.url;
      }
    });
  }

  getPreviousUrl() {
    return this.previousUrl; //TODO : gérer la previousUrl en session, mais d'abord tester si ca fonctionne pas déjà
  }
}
