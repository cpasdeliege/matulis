/// <reference types="@angular/localize" />

import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { CoreModule } from './app/core/core.module';


platformBrowserDynamic().bootstrapModule(CoreModule)
  .catch(err => console.error(err));
