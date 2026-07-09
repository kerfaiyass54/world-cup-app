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

import {
  SimulationService
} from '../../../simulation.service';

@Component({
  selector: 'app-live-match',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './live-match.html',
  styleUrl: './live-match.css'
})
export class LiveMatch
  implements OnInit {

  private route =
    inject(ActivatedRoute);

  private tournamentService =
    inject(TournamentService);

  private teamService =
    inject(TeamService);

  private simulationService =
    inject(SimulationService);

  private location =
    inject(Location);

  isKnockout = false;

  private cdr =
    inject(ChangeDetectorRef);

  tournamentId = '';

  groupId = '';

  matchId = '';

  loading = true;

  simulating = false;

  finished = false;

  match: any = null;

  stats: any = null;

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

  getFlag(teamId: string): string {

    const team =
      this.teamsMap[teamId];

    if (!team) {

      return 'https://flagcdn.com/w40/un.png';
    }

    const code =
      this.flagMap[team.name];

    return code
      ? `https://flagcdn.com/w80/${code}.png`
      : 'https://flagcdn.com/w40/un.png';
  }

  homePossession = 0;
  awayPossession = 0;

  homeShots = 0;
  awayShots = 0;

  homeShotsOnTarget = 0;
  awayShotsOnTarget = 0;

  homeXg = 0;
  awayXg = 0;

  homeCorners = 0;
  awayCorners = 0;

  homeFouls = 0;
  awayFouls = 0;

  standings: any[] = [];

  originalStandings: any[] = [];

  simulationResult: any = null;

  events: any[] = [];

  displayedEvents: any[] = [];

  teamsMap:
    Record<string, any> = {};

  homeScore = 0;

  awayScore = 0;

  currentMinute = 0;

  homeTeam: any = null;

  awayTeam: any = null;

  homePenalties = 0;

  awayPenalties = 0;

  ngOnInit(): void {

    this.tournamentId =
      this.route.snapshot
        .paramMap
        .get('tournamentId') || '';

    this.groupId =
      this.route.snapshot
        .paramMap
        .get('groupId') || '';

    this.matchId =
      this.route.snapshot
        .paramMap
        .get('matchId') || '';

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

          this.loadMatch();
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

        next: standings => {

          this.standings =
            JSON.parse(
              JSON.stringify(
                standings
              )
            );

          this.originalStandings =
            JSON.parse(
              JSON.stringify(
                standings
              )
            );

          this.refreshView();
        }
      });
  }

  updateLiveStandings(): void {

    this.standings =
      JSON.parse(
        JSON.stringify(
          this.originalStandings
        )
      );

    const home =
      this.standings.find(
        s =>
          s.teamId ===
          this.homeTeam.id
      );

    const away =
      this.standings.find(
        s =>
          s.teamId ===
          this.awayTeam.id
      );

    if (
      !home ||
      !away
    ) {
      return;
    }

    home.played++;
    away.played++;

    home.goalsFor +=
      this.homeScore;

    home.goalsAgainst +=
      this.awayScore;

    away.goalsFor +=
      this.awayScore;

    away.goalsAgainst +=
      this.homeScore;

    home.goalDifference =
      home.goalsFor -
      home.goalsAgainst;

    away.goalDifference =
      away.goalsFor -
      away.goalsAgainst;

    if (
      this.homeScore >
      this.awayScore
    ) {

      home.points += 3;
      home.wins++;

      away.losses++;

    }
    else if (
      this.awayScore >
      this.homeScore
    ) {

      away.points += 3;
      away.wins++;

      home.losses++;

    }
    else {

      home.points += 1;
      away.points += 1;

      home.draws++;
      away.draws++;
    }

    this.standings.sort(
      (
        a,
        b
      ) => {

        if (
          b.points !==
          a.points
        ) {
          return (
            b.points -
            a.points
          );
        }

        return (
          b.goalDifference -
          a.goalDifference
        );
      }
    );

    this.refreshView();
  }

  loadMatch(): void {

    this.loadStandings();

    this.tournamentService
      .getMatchById(
        this.tournamentId,
        this.groupId,
        this.matchId
      )
      .subscribe({

        next: (match: any) => {

          this.match =
            match;

          this.isKnockout =
            !!match.knockoutType;

          this.homeTeam =
            this.teamsMap[
              match.teamHomeId
              ];

          this.awayTeam =
            this.teamsMap[
              match.teamAwayId
              ];

          this.loading =
            false;

          this.refreshView();
        },

        error: error => {

          console.error(error);

          this.loading = false;

          this.refreshView();
        }
      });
  }

  simulatePenalties(): void {

    this.homePenalties = 0;

    this.awayPenalties = 0;

    for (
      let i = 0;
      i < 5;
      i++
    ) {

      if (
        Math.random() > 0.25
      ) {

        this.homePenalties++;
      }

      if (
        Math.random() > 0.25
      ) {

        this.awayPenalties++;
      }
    }

    while (
      this.homePenalties ===
      this.awayPenalties
      ) {

      if (
        Math.random() > 0.5
      ) {

        this.homePenalties++;
      }

      if (
        Math.random() > 0.5
      ) {

        this.awayPenalties++;
      }
    }

    this.refreshView();
  }

  getWinnerTeamId() {

    if (
      this.homeScore >
      this.awayScore
    ) {

      return this.homeTeam.id;
    }

    if (
      this.awayScore >
      this.homeScore
    ) {

      return this.awayTeam.id;
    }

    return
    this.homePenalties >
    this.awayPenalties
      ? this.homeTeam.id
      : this.awayTeam.id;
  }

  simulate(): void {

    if (!this.match) {
      return;
    }

    this.simulating = true;

    const payload = {

      match_id:
      this.match.id,

      tournament_id:
      this.tournamentId,

      home_team_id:
      this.homeTeam.id,

      away_team_id:
      this.awayTeam.id,

      home_team_name:
      this.homeTeam.name,

      away_team_name:
      this.awayTeam.name,

      home_ability:
      this.homeTeam.ability,

      away_ability:
      this.awayTeam.ability,

      home_morale:
      this.homeTeam.moral,

      away_morale:
      this.awayTeam.moral
    };

    this.simulationService
      .simulateMatch(payload)
      .subscribe({

        next: (result) => {

          this.simulationResult =
            result;

          this.loadEvents();
        },

        error: error => {

          console.error(error);

          this.simulating = false;

          this.refreshView();
        }
      });
  }

  loadEvents(): void {

    this.simulationService
      .getEvents(
        this.matchId
      )
      .subscribe({

        next: (events: any[]) => {

          this.events =
            [...events]
              .sort(
                (a, b) =>
                  a.minute -
                  b.minute
              );

          this.replayEvents();
        },

        error: error => {

          console.error(error);

          this.simulating = false;

          this.refreshView();
        }
      });
  }

  loadStats(): void {

    this.simulationService
      .getStats(
        this.matchId
      )
      .subscribe({

        next: (stats: any) => {

          this.stats = stats;

          this.refreshView();
        },

        error: error => {

          console.error(error);
        }
      });
  }

  replayEvents(): void {

    this.displayedEvents = [];

    this.homeScore = 0;

    this.awayScore = 0;

    let index = 0;

    const interval =
      setInterval(() => {

        if (
          index >=
          this.events.length
        ) {

          clearInterval(
            interval
          );

          if (
            this.isKnockout &&
            this.homeScore ===
            this.awayScore
          ) {

            this.simulatePenalties();
          }

          this.finished =
            true;

          this.simulating =
            false;

          this.loadStats();

          this.refreshView();

          return;
        }

        const event =
          this.events[index];

        this.currentMinute =
          event.minute;

        this.displayedEvents
          .push(event);

        if (
          event.event_type ===
          'GOAL'
        ) {

          if (
            event.team_id ===
            this.homeTeam.id
          ) {

            this.homeScore++;
          }
          else {

            this.awayScore++;
          }

          if (
            !this.isKnockout
          ) {

            this.updateLiveStandings();
          }
        }

        index++;

        this.refreshView();

        setTimeout(() => {

          const timeline =
            document.querySelector(
              '.timeline'
            );

          if (timeline) {

            timeline.scrollTop =
              timeline.scrollHeight;
          }

        }, 50);

      }, 1000);
  }

  saveResult(): void {

    if (
      !this.simulationResult
    ) {
      return;
    }

    const payload = {

      ...this.simulationResult,

      winnerTeamId:
        this.getWinnerTeamId(),

      penaltiesHome:
      this.homePenalties,

      penaltiesAway:
      this.awayPenalties
    };

    this.tournamentService
      .saveMatchResult(
        this.tournamentId,
        this.groupId,
        this.matchId,
        payload
      )
      .subscribe({

        next: () => {

            sessionStorage.setItem(
              'bracket-updated',
              'true'
            );

          this.goBack();
        },

        error: error => {

          console.error(error);
        }
      });
  }

  goBack(): void {

    this.location.back();
  }

  isHomeEvent(
    event: any
  ): boolean {

    return (
      event.team_id ===
      this.homeTeam?.id
    );
  }

  getEventIcon(
    type: string
  ): string {

    switch (type) {

      case 'GOAL':
        return '⚽';

      case 'YELLOW_CARD':
        return '🟨';

      case 'RED_CARD':
        return '🟥';

      case 'SUBSTITUTION':
        return '🔄';

      case 'SHOT':
        return '🎯';

      case 'FOUL':
        return '🚫';

      default:
        return '📌';
    }
  }
}
