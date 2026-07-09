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
  selector: 'app-tournament-statistics',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './tournament-statistics.html',
  styleUrl: './tournament-statistics.css'
})
export class TournamentStatistics
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

  loading = true;

  statistics: any = null;

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

          this.loadStatistics();

          this.refreshView();
        },

        error: error => {

          console.error(error);

          this.loadStatistics();

          this.refreshView();
        }
      });
  }

  loadStatistics(): void {

    this.tournamentService
      .getStatistics(
        this.tournamentId
      )
      .subscribe({

        next: (statistics: any) => {

          this.statistics =
            statistics;

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

  getTeamName(
    teamId: string
  ): string {

    return this.teamsMap[
      teamId
      ]?.name || 'Unknown';
  }

  getGoalPercentage(
    goals: number
  ): number {

    if (
      !this.statistics ||
      this.statistics.totalGoals === 0
    ) {
      return 0;
    }

    return (
      goals /
      this.statistics.totalGoals
    ) * 100;
  }

  goBack(): void {

    this.location.back();
  }
}
