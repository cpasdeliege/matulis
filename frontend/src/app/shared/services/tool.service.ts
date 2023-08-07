import { Injectable } from '@angular/core';
import { Subject, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ToolService {

	constructor() {}
	
	unsubscribe(unsubscribe$:Subject<void>) {
		unsubscribe$.next();
		unsubscribe$.complete();
	}
}
