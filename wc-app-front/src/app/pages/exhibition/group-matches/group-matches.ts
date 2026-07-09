import {
  Component,
  OnInit,
  inject,
  ChangeDetectorRef
} from '@angular/core';

import {
  CommonModule,
  Location
} from '@angular/common';

import { ActivatedRoute, Router } from '@angular/router';

import {
  TournamentService
} from '../../../tournament.service';

import {
  TeamService
} from '../../../team.service';
import { SimulationService } from '../../../simulation.service';

@Component({
  selector: 'app-group-matches',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './group-matches.html',
  styleUrl: './group-matches.css'
})
export class GroupMatches
  implements OnInit {

  private route =
    inject(ActivatedRoute);

  simulatingMatchId: string | null =
    null;
  private location =
    inject(Location);

  private tournamentService =
    inject(TournamentService);

  private simulationMatches = inject(SimulationService);

  private teamService =
    inject(TeamService);

  private router = inject(Router);

  private cdr =
    inject(ChangeDetectorRef);

  matches: any[] = [];

  teamsMap:
    Record<string, any> = {};

  loading = true;

  tournamentId = '';

  groupId = '';

  flagMap:
    Record<string, string> = {

    'Argentina': 'ar',
    'France': 'fr',
    'Spain': 'es',
    'England': 'gb-eng',
    'Brazil': 'br',
    'Portugal': 'pt',
    'Germany': 'de',
    'Netherlands': 'nl',
    'Belgium': 'be',
    'Croatia': 'hr',
    'Uruguay': 'uy',
    'Colombia': 'co',
    'Morocco': 'ma',
    'Japan': 'jp',
    'Switzerland': 'ch',
    'Austria': 'at',
    'Norway': 'no',
    'Mexico': 'mx',
    'USA': 'us',
    'Türkiye': 'tr',
    'Sweden': 'se',
    'Senegal': 'sn',
    'Iran': 'ir',
    'Ecuador': 'ec',
    'Paraguay': 'py',
    'Egypt': 'eg',
    'Canada': 'ca',
    'South Korea': 'kr',
    'Algeria': 'dz',
    'Ivory Coast': 'ci',
    'Scotland': 'gb-sct',
    'Tunisia': 'tn',
    'Ghana': 'gh',
    'Czechia': 'cz',
    'Bosnia and Herzegovina': 'ba',
    'Australia': 'au',
    'Saudi Arabia': 'sa',
    'Panama': 'pa',
    'Jordan': 'jo',
    'Iraq': 'iq',
    'Uzbekistan': 'uz',
    'South Africa': 'za',
    'Cape Verde': 'cv',
    'DR Congo': 'cd',
    'Qatar': 'qa',
    'Haiti': 'ht',
    'Curacao': 'cw',
    'New Zealand': 'nz'
  };
  openMatch(
    match: any
  ): void {

    this.router.navigate([
      '/exhibition',
      this.tournamentId,
      'group',
      this.groupId,
      'match',
      match.id
    ]);

  }
  simulateMatch(
    match: any
  ): void {

    console.log("Clicked")

    this.simulatingMatchId =
      match.id;

    this.simulationMatches
      .simulateMatch(
        match.id
      )
      .subscribe({

        next: () => {

          this.simulatingMatchId =
            null;

          this.router.navigate([
            '/exhibition',
            this.tournamentId,
            'group',
            this.groupId,
            'match',
            match.id
          ]);
          this.cdr.detectChanges();
        },

        error: error => {

          console.error(error);
          this.cdr.detectChanges();
          this.simulatingMatchId =
            null;
        }
      });
  }

  ngOnInit(): void {

    this.tournamentId =
      this.route.snapshot
        .paramMap
        .get('tournamentId') || '';

    this.groupId =
      this.route.snapshot
        .paramMap
        .get('groupId') || '';

    this.loadTeams();
  }

  private refreshView(): void {

    this.cdr.detectChanges();
  }

  loadTeams(): void {

    this.teamService
      .getAll()
      .subscribe({

        next: (response: any) => {

          response.forEach(
            (team: any) => {

              this.teamsMap[
                team.id
                ] = team;
            }
          );

          this.loadMatches();
        },

        error: error => {

          console.error(error);

          this.loadMatches();
        }
      });
  }

  loadMatches(): void {

    this.tournamentService
      .getGroupMatches(
        this.tournamentId,
        this.groupId
      )
      .subscribe({

        next: (response: any) => {

          this.matches =
            response || [];

          this.loading =
            false;

          this.refreshView();
        },

        error: error => {

          console.error(error);

          this.loading =
            false;

          this.refreshView();
        }
      });
  }

  goBack(): void {

    this.location.back();
  }

  getTeam(
    teamId: string
  ): any {

    return this.teamsMap[
      teamId
      ];
  }

  getTeamName(
    teamId: string
  ): string {

    return this.getTeam(
      teamId
    )?.name || '';
  }

  getFlag(
    teamId: string
  ): string {

    const team =
      this.getTeam(teamId);

    const code =
      this.flagMap[
        team?.name
        ];

    return code
      ? `https://flagcdn.com/w80/${code}.png`
      : 'https://flagcdn.com/w80/un.png';
  }
}
