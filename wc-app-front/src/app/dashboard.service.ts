import { Injectable, inject } from '@angular/core';
import { forkJoin } from 'rxjs';

import { TournamentService }
  from './tournament.service';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private tournamentService =
    inject(TournamentService);

  load(
    tournamentId: string
  ) {

    return forkJoin({

      status:
        this.tournamentService
          .getStatus(
            tournamentId
          ),

      statistics:
        this.tournamentService
          .getStatistics(
            tournamentId
          ),

      bracket:
        this.tournamentService
          .getBracket(
            tournamentId
          ),

      matches:
        this.tournamentService
          .getMatches(
            tournamentId
          )

    });
  }
}
