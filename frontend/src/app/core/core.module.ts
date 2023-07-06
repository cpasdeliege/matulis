import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './core-routing.module';
import { CoreComponent } from './components/core.component';
import { CoreService } from './services/core.service';

@NgModule({
  declarations: [
    CoreComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
	FormsModule,
	HttpClientModule
  ],
  providers: [CoreService],
  bootstrap: [CoreComponent]
})
export class CoreModule { }
