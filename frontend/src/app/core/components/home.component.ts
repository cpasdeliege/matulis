import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { Authentication } from 'src/app/users/model/Authentication';
import { ToolService } from 'src/app/shared/services/tool.service';
import { UserService } from 'src/app/users/services/user.service';

@Component({
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit, OnDestroy {

	private unsubscribe$ = new Subject<void>();
	authentication: Authentication | null = null;
	searchUsername: string = "";

	constructor(
		private toolService: ToolService,
		private userService: UserService,
	) {}

	ngOnInit() {

	}

	ngOnDestroy() {
		this.toolService.unsubscribe(this.unsubscribe$);
	}

	search() {
		this.userService.search(this.searchUsername).subscribe({
			next : (data) => {
				console.log(data)
			}
		})
	}
}