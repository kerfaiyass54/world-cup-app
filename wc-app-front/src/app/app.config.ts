import {
  ApplicationConfig,
  provideBrowserGlobalErrorListeners
} from '@angular/core';

import { provideRouter } from '@angular/router';

import {
  provideHttpClient,
  withInterceptors
} from '@angular/common/http';

import { provideKeycloak } from 'keycloak-angular';

import { routes } from './app.routes';
import { userEmailInterceptor } from '../src/app/interceptors/user-email.interceptor';



export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),

    provideRouter(routes),

    provideHttpClient(
      withInterceptors([
        userEmailInterceptor
      ])
    ),

    provideKeycloak({
      config: {
        url: 'http://localhost:8080',
        realm: 'world-cup',
        clientId: 'world-cup-client'
      },
      initOptions: {
        onLoad: 'login-required'
      }
    })
  ]
};
