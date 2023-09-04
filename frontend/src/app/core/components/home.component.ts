import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { Authentication } from 'src/app/users/model/Authentication';
import { ToolService } from 'src/app/shared/services/tool.service';
import { UserService } from 'src/app/users/services/user.service';
import { User } from 'src/app/users/model/User';
import { instanceToPlain, plainToClass } from 'class-transformer';

@Component({
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit, OnDestroy {

	private unsubscribe$ = new Subject<void>();
	authentication: Authentication | null = null;
	searchUsername: string = "";
	users: any = null;
	selectedUser: User | null = null;

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
		if(this.searchUsername.length >= 3) {
			this.userService.search(this.searchUsername).subscribe({
				next : (data) => {
					this.users = plainToClass(User, data)
					console.log(this.users);
				}
			})
		} else {
			this.users = null;
		}
	}

	select(user:User) {
		this.selectedUser = user;
	}

	updateSelectedUser() {
		this.userService.update(instanceToPlain(this.selectedUser)).subscribe({
			next: (data) => {
				console.log(data);
			}
		})
	}
}