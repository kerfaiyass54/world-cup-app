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

import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';

import {
  TournamentService
} from '../../../tournament.service';

import {
  TeamService
} from '../../../team.service';

@Component({
  selector: 'app-tournament-bracket',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './tournament-bracket.html',
  styleUrl: './tournament-bracket.css'
})
export class TournamentBracket
  implements OnInit {

  private route =
    inject(ActivatedRoute);

  private location =
    inject(Location);

  private tournamentService =
    inject(TournamentService);

  private teamService =
    inject(TeamService);

  private cdr =
    inject(ChangeDetectorRef);

  private router =
    inject(Router);

  tournamentId = '';

  loading = true;

  bracket: any = null;

  teamsMap:
    Record<string, any> = {};

  animatingTeams: string[] = [];

  animateWinner(teamId: string): void {

    if (!teamId) {
      return;
    }

    this.animatingTeams.push(teamId);

    setTimeout(() => {

      this.animatingTeams =
        this.animatingTeams.filter(
          id => id !== teamId
        );

    }, 3000);

  }

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


  refreshBracket(): void {

    this.loading = true;

    this.loadBracket();
  }


  ngOnInit(): void {

    this.tournamentId =
      this.route.snapshot
        .paramMap
        .get('id') || '';

    this.loadTeams();



    this.router.events.subscribe(event => {

      if (
        event instanceof NavigationEnd
      ) {

        const updated =
          sessionStorage.getItem(
            'bracket-updated'
          );

        if (updated === 'true') {

          sessionStorage.removeItem(
            'bracket-updated'
          );

      this.refreshBracket();
        }
      }
    });
  }

  private refreshView(): void {

    this.cdr.detectChanges();
  }

  loadTeams(): void {

    this.teamService
      .getAll()
      .subscribe({

        next: (teams: any[]) => {

          teams.forEach(team => {

            this.teamsMap[
              team.id
              ] = team;
          });

      this.refreshBracket();
        },

        error: error => {

          console.error(error);

      this.refreshBracket();
        }
      });
  }

  loadBracket(): void {

    this.tournamentService
      .getBracket(
        this.tournamentId
      )
      .subscribe({

        next: (response: any) => {

          this.bracket =
            response;
          [
            ...response.round32 || [],
            ...response.round16 || [],
            ...response.quarterFinals || [],
            ...response.semiFinals || [],
            ...response.thirdPlace || [],
            ...response.finals || []
          ]
            .forEach((match: any) => {

              if (match.winnerTeamId) {

                this.animateWinner(
                  match.winnerTeamId
                );

              }

            });

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


  getTournamentProgress(): string {

    if (
      this.bracket?.finals?.[0]
        ?.winnerTeamId
    ) {
      return 'CHAMPION';
    }

    if (
      this.bracket?.finals?.length
    ) {
      return 'FINAL';
    }

    if (
      this.bracket?.thirdPlace?.length ||
      this.bracket?.semiFinals?.length
    ) {
      return 'SEMI_FINAL';
    }

    if (
      this.bracket?.quarterFinals?.length
    ) {
      return 'QUARTER_FINAL';
    }

    if (
      this.bracket?.round16?.length
    ) {
      return 'ROUND_16';
    }

    if (
      this.bracket?.round32?.length
    ) {
      return 'ROUND_32';
    }

    return 'GROUP_STAGE';
  }

  isCompletedStage(
    stage: string
  ): boolean {

    const order = [
      'GROUP_STAGE',
      'ROUND_32',
      'ROUND_16',
      'QUARTER_FINAL',
      'SEMI_FINAL',
      'FINAL',
      'CHAMPION'
    ];

    return (
      order.indexOf(stage)
      <
      order.indexOf(
        this.getTournamentProgress()
      )
    );
  }


  isCurrentStage(
    stage: string
  ): boolean {

    return (
      this.getTournamentProgress()
      === stage
    );
  }

  openKnockoutMatch(
    phaseId: string,
    matchId: string
  ): void {

    this.router.navigate([

      '/exhibition',
      'tournaments',
      this.tournamentId,

      'knockouts',
      phaseId,

      'matches',
      matchId

    ]);
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
    )?.name || 'TBD';
  }

  getFlag(
    teamId: string
  ): string {

    const team =
      this.getTeam(
        teamId
      );

    if (!team) {

      return '';
    }

    const code =
      this.flagMap[
        team.name
        ];

    return code
      ? `https://flagcdn.com/w80/${code}.png`
      : '';
  }

  getWinner(
    match: any
  ): string {

    return match?.winnerTeamId || '';
  }

  getChampion(): any {

    const finalMatch =
      this.bracket?.finals?.[0];

    if (
      !finalMatch?.winnerTeamId
    ) {

      return null;
    }

    return this.getTeam(
      finalMatch.winnerTeamId
    );
  }

  getThirdPlaceWinner(): any {

    const match =
      this.bracket?.thirdPlace?.[0];

    if (
      !match?.winnerTeamId
    ) {

      return null;
    }

    return this.getTeam(
      match.winnerTeamId
    );
  }

  isChampion(
    teamId: string
  ): boolean {

    const champion =
      this.getChampion();

    return champion?.id === teamId;
  }

  isThirdPlaceWinner(
    teamId: string
  ): boolean {

    const team =
      this.getThirdPlaceWinner();

    return team?.id === teamId;
  }

  hasThirdPlaceMatch(): boolean {

    return !!(
      this.bracket?.thirdPlace &&
      this.bracket.thirdPlace.length
    );
  }

  goBack(): void {

    this.location.back();
  }
}
