import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TournamentService {

  private http = inject(HttpClient);

  private api =
    'http://localhost:8010/api/tournaments';

  getMatchById(
    tournamentId: string,
    groupId: string,
    matchId: string
  ) {

    return this.http.get<any[]>(
      `${this.api}/${tournamentId}/groups/${groupId}/matches`
    ).pipe(

      map(matches =>

        matches.find(
          match =>
            match.id === matchId
        )
      )
    );
  }

  saveMatchResult(
    tournamentId: string,
    groupId: string,
    matchId: string,
    result: any
  ) {

    return this.http.post(
      `${this.api}/${tournamentId}/groups/${groupId}/matches/${matchId}/result`,
      result
    );
  }

  createTournament(
    payload: any
  ) {

    return this.http.post<any>(
      this.api,
      payload
    );
  }

  getMatchEvents(
    matchId: string
  ) {

    return this.http.get<any[]>(
      `http://localhost:8010/api/events/${matchId}`
    );
  }

  getTournament(
    tournamentId: string
  ) {

    return this.http.get<any>(
      `${this.api}/${tournamentId}`
    );
  }

  getGroups(
    tournamentId: string
  ) {

    return this.http.get<any>(
      `${this.api}/${tournamentId}/groups`
    );
  }

  getGroup(
    tournamentId: string,
    groupId: string
  ) {

    return this.http.get<any>(
      `${this.api}/${tournamentId}/groups/${groupId}`
    );
  }

  startTournament(
    tournamentId: string
  ) {

    return this.http.post<any>(
      `${this.api}/${tournamentId}/start`,
      {}
    );
  }

  addTeamToGroup(
    tournamentId: string,
    groupId: string,
    teamId: string
  ) {

    return this.http.post<any>(
      `${this.api}/${tournamentId}/groups/${groupId}/teams/${teamId}`,
      {}
    );
  }

  getStandings(
    tournamentId: string,
    groupId: string
  ) {

    return this.http.get<any>(
      `${this.api}/${tournamentId}/groups/${groupId}/standings`
    );
  }

  getKnockouts(
    tournamentId: string
  ) {

    return this.http.get<any>(
      `${this.api}/${tournamentId}/knockouts`
    );
  }

  getStatus(
    tournamentId: string
  ) {

    return this.http.get<any>(
      `${this.api}/${tournamentId}/status`
    );
  }

  getStatistics(
    tournamentId: string
  ) {

    return this.http.get<any>(
      `${this.api}/${tournamentId}/statistics`
    );
  }

  getMatches(
    tournamentId: string
  ) {

    return this.http.get<any>(
      `${this.api}/${tournamentId}/matches`
    );
  }

  getBracket(
    tournamentId: string
  ) {

    return this.http.get<any>(
      `${this.api}/${tournamentId}/bracket`
    );
  }

  getTeamJourney(
    tournamentId: string,
    teamId: string
  ) {

    return this.http.get<any>(
      `${this.api}/${tournamentId}/teams/${teamId}/journey`
    );
  }

  getGroupMatches(
    tournamentId: string,
    groupId: string
  ) {

    return this.http.get<any[]>(

      `${this.api}/${tournamentId}/groups/${groupId}/matches`

    );
  }

  getTournamentDetails(id: string) {

    return this.http.get(
      `${this.api}/${id}/details`
    );
  }

  getByEmail(
    email: string
  ) {

    return this.http.get<any>(
      `http://localhost:8010/api/tournaments/email/${email}`
    );
  }
}
