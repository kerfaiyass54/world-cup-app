import { Routes } from '@angular/router';

export const routes: Routes = [

  {
    path: '',
    redirectTo: 'welcome',
    pathMatch: 'full'
  },

  {
    path: 'welcome',
    loadComponent: () =>
      import('./pages/welcome/welcome')
        .then(m => m.Welcome)
  },

  {
    path: 'dashboard',

    loadComponent: () =>
      import('./pages/dashboard/dashboard')
        .then(m => m.Dashboard),

    children: [

      {
        path: '',
        redirectTo: 'wc2026',
        pathMatch: 'full'
      },

      {
        path: 'wc2026',
        loadComponent: () =>
          import('./pages/wc2026/wc2026')
            .then(m => m.Wc2026)
      },

      {
        path: 'nations-info',
        loadComponent: () =>
          import('./pages/nations-info/nations-info')
            .then(m => m.NationsInfo)
      },

      {
        path: 'tournament-info',
        loadComponent: () =>
          import('./pages/tournament-info/tournament-info')
            .then(m => m.TournamentInfo)
      },

      {
        path: 'head-to-head',
        loadComponent: () =>
          import('./pages/head-to-head/head-to-head')
            .then(m => m.HeadToHead)
      }

    ]

  }

];
