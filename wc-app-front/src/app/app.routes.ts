import { Routes } from '@angular/router';

export const routes: Routes = [

  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full'
  },

  {
    path: 'dashboard',
    loadComponent: () =>
      import('./pages/dashboard/dashboard')
        .then(m => m.Dashboard)
  },

  {
    path: 'ai-chat',
    loadComponent: () =>
      import('./pages/ai-chat/ai-chat')
        .then(m => m.AiChat)
  },

  {
    path: 'predictions',
    loadComponent: () =>
      import('./pages/predictions/predictions')
        .then(m => m.Predictions)
  },

  {
    path: 'exhibition',
    loadComponent: () =>
      import('./pages/exhibition/exhibition')
        .then(m => m.Exhibition)
  },

  {
    path: 'home-page',
    loadComponent: () =>
      import('./pages/home-page/home-page')
        .then(m => m.HomePage)
  },

  {
    path: '**',
    redirectTo: 'dashboard'
  }

];
