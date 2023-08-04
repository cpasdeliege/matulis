import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NavigationService } from './services/navigation.service';

@NgModule({
	imports:      [ CommonModule ],
	declarations: [  ],
	exports:      [ CommonModule, FormsModule ],
	providers:    [ NavigationService ]
})
export class SharedModule { }