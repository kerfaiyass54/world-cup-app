import {
  Component,
  OnInit,
  inject
} from '@angular/core';

import {
  CommonModule
} from '@angular/common';

import {
  TeamService
} from '../../../team.service';

import {
  TournamentService
} from '../../../tournament.service';

import {
  ChangeDetectorRef
} from '@angular/core';

import {
  Router
} from '@angular/router';

@Component({
  selector: 'app-tournament-create',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './tournament-create.html',
  styleUrl: './tournament-create.css'
})
export class TournamentCreate
  implements OnInit {

  private teamService =
    inject(TeamService);

  private cdr =
    inject(ChangeDetectorRef);

  private tournamentService =
    inject(TournamentService);



  private router =
    inject(Router);

  loading = false;

  saving = false;

  errorMessage = '';

  currentStep = 1;

  teams: any[] = [];

  groups: any[] = [];

  draggedTeam: any = null;

  draggedFromGroup: any = null;

  selectedTeamsCount = 0;

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

  ngOnInit(): void {

    this.buildGroups();

    this.loadTeams();
  }

  loadTeams(): void {

    this.loading = true;

    this.teamService
      .getAll()
      .subscribe({

        next: (response: any) => {

          this.teams =
            [...(response || [])];

          this.loading = false;

          this.refreshView();
        },

        error: (error) => {

          console.error(error);

          this.refreshView();


          this.loading = false;
        }
      });
  }

  buildGroups(): void {

    this.groups = Array.from(
        { length: 12 },
        (_, index) => ({

          name:
              String.fromCharCode(
                  65 + index
              ),

          teams: []
        })
    );

    this.refreshView();
  }

  onDragStart(
    team: any,
    sourceGroup: any = null
  ): void {

    this.draggedTeam =
      team;

    this.draggedFromGroup =
      sourceGroup;
  }

  allowDrop(
    event: DragEvent
  ): void {

    event.preventDefault();
  }

  dropToGroup(group: any): void {

    if (!this.draggedTeam) {
      return;
    }

    this.errorMessage = '';

    if (group.teams.length >= 4) {

      this.errorMessage =
          'Group already contains 4 teams';

      return;
    }

    const valid =
        this.validateRules(
            this.draggedTeam,
            group.teams
        );

    if (!valid) {
      return;
    }

    if (this.draggedFromGroup) {

      this.draggedFromGroup.teams =
          this.draggedFromGroup.teams.filter(
              (t: any) =>
                  t.id !== this.draggedTeam.id
          );

    } else {

      this.teams =
          this.teams.filter(
              t =>
                  t.id !== this.draggedTeam.id
          );
    }

    group.teams = [
      ...group.teams,
      this.draggedTeam
    ];

    this.resetDrag();

    this.refreshView();
  }

  private refreshView(): void {

    this.groups = [...this.groups];

    this.teams = [...this.teams];

    this.updateCounters();

    this.cdr.detectChanges();
  }

  dropToAvailableTeams(): void {

    if (!this.draggedTeam) {
      return;
    }

    if (this.draggedFromGroup) {

      this.draggedFromGroup.teams =
          this.draggedFromGroup.teams.filter(
              (t: any) =>
                  t.id !== this.draggedTeam.id
          );

      this.teams = [
        ...this.teams,
        this.draggedTeam
      ];
    }

    this.resetDrag();

    this.refreshView();
  }

  resetDrag(): void {

    this.draggedTeam = null;

    this.draggedFromGroup = null;
  }

  validateRules(
    team: any,
    groupTeams: any[]
  ): boolean {

    const continent =
      team.continent;

    if (
      continent ===
      'EUROPE'
    ) {

      const europeCount =
        groupTeams.filter(
          t =>
            t.continent ===
            'EUROPE'
        ).length;

      if (
        europeCount >= 2
      ) {

        this.errorMessage =
          'Maximum 2 European teams allowed';

        return false;
      }

      return true;
    }

    const sameContinent =
      groupTeams.filter(
        t =>
          t.continent ===
          continent
      ).length;

    if (
      sameContinent >= 1
    ) {

      this.errorMessage =
        `Only one ${continent} team allowed`;

      return false;
    }

    return true;
  }

  updateCounters(): void {

    this.selectedTeamsCount =

      this.groups.reduce(
        (
          total,
          group
        ) =>
          total +
          group.teams.length,
        0
      );
  }

  isGroupComplete(
    group: any
  ): boolean {

    return (
      group.teams.length === 4
    );
  }

  allGroupsComplete(): boolean {

    return this.groups.every(
      group =>
        group.teams.length === 4
    );
  }

  getCompletionPercentage(): number {

    return Math.round(
      (
        this.selectedTeamsCount /
        48
      ) * 100
    );
  }

  reviewTournament(): void {

    if (
      !this.allGroupsComplete()
    ) {

      this.errorMessage =
        'All groups must contain 4 teams';

      return;
    }

    this.currentStep = 2;
  }

  backToEditor(): void {

    this.currentStep = 1;
  }

  saveTournament(): void {

    const email =
      sessionStorage.getItem(
        'email'
      );

    if (!email) {

      alert(
        'No user email found'
      );

      return;
    }

    const payload = {

      email,

      startDate:
        new Date()
          .toISOString()
          .split('T')[0],

      groups:

        this.groups.map(
          (group: any) => ({

            name:
            group.name,

            teamIds:

              group.teams.map(
                (team: any) =>
                  team.id
              )
          })
        )
    };

    this.saving = true;

    this.tournamentService
      .createTournament(
        payload
      )
      .subscribe({

        next: () => {

          this.saving = false;

          this.refreshView();

          this.router.navigate(
            ['/exhibition']
          );
        },

        error: (error) => {

          console.error(
            error
          );

          this.saving = false;
        }
      });
  }

  getGroupAbility(
    group: any
  ): number {

    return group.teams.reduce(
      (
        total: number,
        team: any
      ) =>
        total +
        team.ability,
      0
    );
  }
}
