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

import {
  ActivatedRoute
} from '@angular/router';

import {
  TournamentService
} from '../../../tournament.service';

import {
  TeamService
} from '../../../team.service';

@Component({
  selector: 'app-group-standings',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './group-standings.html',
  styleUrl: './group-standings.css'
})
export class GroupStandings
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

  tournamentId = '';

  groupId = '';

  flagMap: Record<string, string> = {

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

  loading = true;

  standings: any[] = [];

  group: any = null;

  teamsMap:
    Record<string, any> = {};

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

        next: (teams: any[]) => {

          teams.forEach(team => {

            this.teamsMap[
              team.id
              ] = team;

          });

          this.refreshView();

          this.loadGroup();

        },

        error: error => {

          console.error(error);

          this.loading = false;

          this.refreshView();

        }
      });
  }

  loadGroup(): void {

    this.tournamentService
      .getGroup(
        this.tournamentId,
        this.groupId
      )
      .subscribe({

        next: (group: any) => {

          this.group =
            group;

          this.refreshView();

          this.loadStandings();

        },

        error: error => {

          console.error(error);

          this.loading = false;

          this.refreshView();

        }
      });
  }

  loadStandings(): void {

    this.tournamentService
      .getStandings(
        this.tournamentId,
        this.groupId
      )
      .subscribe({

        next: (
          standings: any[]
        ) => {

          this.standings =
            this.sortStandings(
              standings
            );

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

  private sortStandings(
    standings: any[]
  ): any[] {

    return [...standings]
      .sort((a, b) => {

        if (
          b.points !==
          a.points
        ) {

          return (
            b.points -
            a.points
          );
        }

        const gdA =
          a.goalsFor -
          a.goalsAgainst;

        const gdB =
          b.goalsFor -
          b.goalsAgainst;

        if (
          gdB !==
          gdA
        ) {

          return (
            gdB -
            gdA
          );
        }

        return (
          b.goalsFor -
          a.goalsFor
        );

      });
  }

  isQualified(
    index: number
  ): boolean {

    return index < 2;

  }

  isEliminated(
    index: number
  ): boolean {

    return index >= 2;

  }

  getGroupName(): string {

    return this.group?.name
      ? `Group ${this.group.name}`
      : 'Group Stage';

  }

  getTeam(
    teamId: string
  ): any {

    return this.teamsMap[
      teamId
      ];

  }

  getFlag(
    teamId: string
  ): string {

    const team =
      this.getTeam(
        teamId
      );

    if (!team) {

      return 'https://flagcdn.com/w40/un.png';
    }

    const code =
      this.flagMap[
        team.name
        ];

    return code
      ? `https://flagcdn.com/w40/${code}.png`
      : 'https://flagcdn.com/w40/un.png';
  }

  getGoalDifference(
    standing: any
  ): number {

    return (
      standing.goalsFor -
      standing.goalsAgainst
    );

  }

  getLeader(): any {

    if (
      !this.standings.length
    ) {

      return null;

    }

    return this.getTeam(
      this.standings[0]
        .teamId
    );
  }

  getBestAttack(): number {

    if (
      !this.standings.length
    ) {

      return 0;

    }

    return Math.max(
      ...this.standings.map(
        s => s.goalsFor
      )
    );
  }

  getBestDefense(): number {

    if (
      !this.standings.length
    ) {

      return 0;

    }

    return Math.min(
      ...this.standings.map(
        s => s.goalsAgainst
      )
    );
  }

  goBack(): void {

    this.location.back();

  }
}
