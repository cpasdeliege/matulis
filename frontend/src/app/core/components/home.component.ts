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
	updateLock:boolean = true;
	newEmployeeId:any = null;
	authentication: Authentication | null = null;
	searchUsername: string = "";
	users: any = null;
	selectedUser: User | null = null;
	searchTimeout:any = null;

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
		clearTimeout(this.searchTimeout);
		this.toolService.unsubscribe(this.unsubscribe$);

		this.searchTimeout = setTimeout(() => {
			if(this.searchUsername.length >= 3) {
				this.loadingSearch = true;
				this.userService.search(this.searchUsername).pipe(takeUntil(this.unsubscribe$)).subscribe({
					next : (data) => {
						this.unselect(); // on déselectionne après une recherche pour éviter des erreurs d'encodage
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
		}, 250)
	}

	select(user:User) {
		this.selectedUser = user;
		this.resetForm(user.getEmployeeId());
	}

	unselect() {
		this.selectedUser = null;
		this.resetForm();
	}

	lock(bool:boolean) {
		this.updateLock = bool;
	}

	updateSelectedUser() {
		this.loadingEmployeeId = true;
		let userData = instanceToPlain(this.selectedUser);
		userData['employeeId'] = this.newEmployeeId;
		this.userService.update(userData).subscribe({
			next: (data) => {
				console.log(data);
				this.toastService.show('Matricule modifié', { type: 'success' });
				this.loadingEmployeeId = false;
				this.selectedUser?.setEmployeeId(data.employeeId);
				this.resetForm(data.employeeId);
			},
			error : () =>  {
				this.toastService.show('Erreur lors de la modification', { type: 'danger' });
				this.loadingEmployeeId = false;
			}
		})
	}

	resetForm(employeeId:any = null) {
		this.updateLock = true;
		this.newEmployeeId = employeeId;
	}
}