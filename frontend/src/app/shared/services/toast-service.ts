import { Injectable, TemplateRef } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ToastService {
	toasts: any[] = [];

	show(textOrTpl: string | TemplateRef<any>, options: any = {}) {
		switch(options.type) {
			case 'success': 
				options.classname = 'bg-success text-white';
				options.icon = 'done';
				break;
			case 'danger':
				options.classname = 'bg-danger text-white';
				options.icon = 'error_outline';
				break;
		}
		this.toasts.push({ textOrTpl, ...options });
	}

	remove(toast:any) {
		this.toasts = this.toasts.filter((t) => t !== toast);
	}

	clear() {
		this.toasts.splice(0, this.toasts.length);
	}
}