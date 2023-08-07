import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { Authentication } from 'src/app/authentication/model/Authentication';
import { ToolService } from 'src/app/shared/services/tool.service';

@Component({
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit, OnDestroy {

	private unsubscribe$ = new Subject<void>();
	authentication: Authentication | null = null;

	constructor(
		private toolService:ToolService
	) {}

	ngOnInit() {

	}

	ngOnDestroy() {
		this.toolService.unsubscribe(this.unsubscribe$);
	}

}