import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SimulationService {

  private http = inject(HttpClient);

  private api =
    'http://localhost:8000';

  simulateMatch(
    payload: any
  ) {

    return this.http.post<any>(
      `${this.api}/simulate`,
      payload
    );
  }

  getEvents(
    matchId: string
  ) {

    return this.http.get<any>(
      `${this.api}/matches/${matchId}/events`
    );
  }

  getStats(
    matchId: string
  ) {

    return this.http.get<any>(
      `${this.api}/matches/${matchId}/stats`
    );
  }
}
