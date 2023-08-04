import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home.component';
import { authenticationGuard } from '../authentication/guards/authentication.guard';
import { TestComponent } from './components/test.component';

const routes: Routes = [
	{ path: '', pathMatch: 'full', redirectTo: 'home' },
  	{ path: 'home', component: HomeComponent, canActivate: [authenticationGuard] },
  	{ path: 'test', component: TestComponent, canActivate: [authenticationGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
