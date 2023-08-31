import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login.component';
import { authenticationExistsGuard } from './guards/authentication-exists.guard';

const routes: Routes = [
	{ path: 'login', component: LoginComponent, canActivate: [authenticationExistsGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AuthenticationRoutingModule { }
