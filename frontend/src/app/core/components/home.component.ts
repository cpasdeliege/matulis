import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { Authentication } from 'src/app/users/model/Authentication';
import { ToolService } from 'src/app/shared/services/tool.service';
import { UserService } from 'src/app/users/services/user.service';
import { User } from 'src/app/users/model/User';
import { instanceToPlain, plainToClass } from 'class-transformer';
import { ToastService } from 'src/app/shared/services/toast-service';

@Component({
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit, OnDestroy {

	private unsubscribe$ = new Subject<void>();
	loadingSearch: boolean = false;
	loadingEmployeeId: boolean = false;
	authentication: Authentication | null = null;
	searchUsername: string = "";
	users: any = null;
	selectedUser: User | null = null;

	constructor(
		private toolService: ToolService,
		private userService: UserService,
		private toastService: ToastService,
	) {}

	ngOnInit() {

	}

	ngOnDestroy() {
		this.toolService.unsubscribe(this.unsubscribe$);
	}

	search() {
		this.toolService.unsubscribe(this.unsubscribe$);
		if(this.searchUsername.length >= 3) {
			this.loadingSearch = true;
			this.userService.search(this.searchUsername).pipe(takeUntil(this.unsubscribe$)).subscribe({
				next : (data) => {
					this.users = plainToClass(User, data)
					this.loadingSearch = false;
				},
				error : () => {
					this.loadingSearch = false;
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
		this.loadingEmployeeId = true;
		this.userService.update(instanceToPlain(this.selectedUser)).subscribe({
			next: (data) => {
				console.log(data);
				this.toastService.show('Matricule modifié', { classname: 'bg-success text-light', delay: 3000 });
				this.loadingEmployeeId = false;
			},
			error : () =>  {
				this.loadingEmployeeId = false;
			}
		})
	}

	showSuccessTest(){
		this.toastService.show('Matricule modifié', { classname: 'bg-success text-light', delay: 3000 });
	}
}