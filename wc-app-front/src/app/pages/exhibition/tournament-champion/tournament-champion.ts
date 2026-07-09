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
  selector: 'app-tournament-champion',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './tournament-champion.html',
  styleUrl: './tournament-champion.css'
})
export class TournamentChampion
  implements OnInit {

  private route =
    inject(ActivatedRoute);

  championTeamId = '';

  championStats = {

    matchesPlayed: 0,

    wins: 0,

    goals: 0
  };

  roadToGlory: any[] = [];

  private location =
    inject(Location);

  private tournamentService =
    inject(TournamentService);

  private teamService =
    inject(TeamService);

  private cdr =
    inject(ChangeDetectorRef);

  tournamentId = '';

  loading = true;

  teamsMap:
    Record<string, any> = {};

  ngOnInit(): void {

    this.tournamentId =
      this.route.snapshot
        .paramMap
        .get('id') || '';

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

          this.loadChampion();
        },

        error: error => {

          console.error(error);

          this.loadChampion();
        }
      });
  }

  loadChampion(): void {

    this.tournamentService
      .getBracket(
        this.tournamentId
      )
      .subscribe({

        next: (bracket: any) => {

          const finalMatch =
            bracket?.finals?.[0];

          if (
            finalMatch &&
            finalMatch.winnerTeamId
          ) {

            this.championTeamId =
              finalMatch.winnerTeamId;

            this.calculateChampionStats(
              bracket
            );
          }

          this.loading = false;

          this.refreshView();
        },

        error: error => {

          console.error(error);

          this.loading = false;

          this.refreshView();
        }
      });
  }

  private calculateChampionStats(
    bracket: any
  ): void {

    const phases = [

      ...(bracket.round32 || []),

      ...(bracket.round16 || []),

      ...(bracket.quarterFinals || []),

      ...(bracket.semiFinals || []),

      ...(bracket.finals || [])
    ];

    this.championStats = {

      matchesPlayed: 0,

      wins: 0,

      goals: 0
    };

    this.roadToGlory = [];

    phases.forEach(match => {

      const involved =

        match.teamHomeId ===
        this.championTeamId ||

        match.teamAwayId ===
        this.championTeamId;

      if (!involved) {

        return;
      }

      this.championStats.matchesPlayed++;

      if (
        match.winnerTeamId ===
        this.championTeamId
      ) {

        this.championStats.wins++;
      }

      const goals =

        match.teamHomeId ===
        this.championTeamId

          ? match.goalsHome

          : match.goalsAway;

      this.championStats.goals += goals;

      this.roadToGlory.push({

        opponent:

          match.teamHomeId ===
          this.championTeamId

            ? match.teamAwayId

            : match.teamHomeId,

        goals,

        phase:
          this.getPhaseName(match)
      });

    });
  }

  getTeamName(
    teamId: string
  ): string {

    return this.teamsMap[
      teamId
      ]?.name || 'Unknown';
  }


  private getPhaseName(
    match: any
  ): string {

    if (
      match.phaseType ===
      'ROUND_32'
    ) {

      return 'Round of 32';
    }

    if (
      match.phaseType ===
      'ROUND_16'
    ) {

      return 'Round of 16';
    }

    if (
      match.phaseType ===
      'QUARTER_FINAL'
    ) {

      return 'Quarter Final';
    }

    if (
      match.phaseType ===
      'SEMI_FINAL'
    ) {

      return 'Semi Final';
    }

    if (
      match.phaseType ===
      'FINAL'
    ) {

      return 'Final';
    }

    return 'Knockout Match';
  }

  goBack(): void {

    this.location.back();
  }
}
