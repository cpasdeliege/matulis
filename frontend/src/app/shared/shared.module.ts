import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ToastsContainer } from './components/toasts-container.component';

@NgModule({
	imports:      [ CommonModule, ToastsContainer ],
	declarations: [ ],
	exports:      [ CommonModule, FormsModule, ToastsContainer ],
	providers:    [ ]
})
export class SharedModule { }