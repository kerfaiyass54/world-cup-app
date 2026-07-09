import { Routes } from '@angular/router';
import { MatchCenter } from './pages/exhibition/match-center/match-center';
import { LiveMatch } from './pages/exhibition/live-match/live-match';
import { TournamentBracket } from './pages/exhibition/tournament-bracket/tournament-bracket';
import { TournamentStatistics } from './pages/exhibition/tournament-statistics/tournament-statistics';
import { TournamentChampion } from './pages/exhibition/tournament-champion/tournament-champion';


export const routes: Routes = [

  {
    path: '',
    redirectTo: 'home-page',
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
  },{
    path: 'add-predictions',
    loadComponent: () =>
      import('./pages/predictions/predictions')
        .then(m => m.Predictions)
  },

  {
    path: 'predictions',
    loadComponent: () =>
      import('./pages/predictions/check-predictions/check-predictions')
        .then(m => m.CheckPredictions)
  },
  {
    path: 'exhibition/create',
    loadComponent: () =>
      import('./pages/exhibition/tournament-create/tournament-create')
        .then(m => m.TournamentCreate)
  },{
    path:
      'tournaments/:tournamentId/bracket',

    component:
    TournamentBracket
  },{
    path: ':id/statistics',
    component: TournamentStatistics
  },
  {
    path: ':id/champion',
    component: TournamentChampion
  },

  {
    path: 'exhibition/:id',
    loadComponent: () =>
      import('./pages/exhibition/tournament-details/tournament-details')
        .then(m => m.TournamentDetails)
  },{
    path: ':tournamentId/groups/:groupId',
    loadComponent: () =>
      import(
        './pages/exhibition/group-matches/group-matches'
        ).then(
        m => m.GroupMatches
      )
  },{
    path:
      'exhibition/:tournamentId/group/:groupId/match/:matchId',
    component:
    LiveMatch
  },{
    path:
      'exhibition/tournaments/:tournamentId/groups/:groupId/standings',

    loadComponent: () =>
      import(
        './pages/exhibition/group-standings/group-standings'
        )
        .then(
          m => m.GroupStandings
        )
  },{
    path:
      'exhibition/:tournamentId/group/:groupId/match/:matchId',
    component:
    MatchCenter
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
    path: 'profile-add',
    loadComponent: () =>
      import('./pages/profile-creation/profile-creation')
        .then(m => m.ProfileCreation)
  },{
    path: 'profile-check',
    loadComponent: () =>
      import('./pages/user-profile/user-profile')
        .then(m => m.UserProfile)
  },

  {
    path: '**',
    redirectTo: 'dashboard'
  }

];
