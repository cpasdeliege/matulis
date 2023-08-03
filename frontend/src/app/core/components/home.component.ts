import { Component, OnInit } from '@angular/core';
import { CoreService } from '../services/core.service';
import { HttpClient } from '@angular/common/http';

@Component({
  templateUrl: './home.component.html'
})
export class HomeComponent {

  title = 'Demo';
  greeting:any = {};

  constructor(private coreService: CoreService, private http: HttpClient) {
    
  }

  authenticated() { return this.coreService.authenticated; }

}