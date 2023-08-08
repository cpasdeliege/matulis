import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';


@Component({
  templateUrl: './test.component.html'
})
export class TestComponent implements OnInit {

	msg:any;

	constructor(private http:HttpClient) {

	}

	ngOnInit() {
		
	}

	hello() {
		this.http.get('http://cpl-app-27:8080/api/hello',{ responseType: 'text' }).subscribe({
			next: (data) => {
				console.log('HELLO')
				this.msg = data;
			}
		})
	}

	test() {
		this.http.get('http://cpl-app-27:8080/api/test').subscribe({
			next: (data) => {
				console.log('TEST')
				this.msg = data;
			}
		})
	}
}