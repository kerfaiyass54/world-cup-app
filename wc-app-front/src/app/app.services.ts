import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ApiService {

  base = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  champions() {
    return this.http.get<any>(`${this.base}/champions`);
  }

  championsEra() {
    return this.http.get<any>(`${this.base}/champions/era`);
  }

  goalsEra() {
    return this.http.get<any>(`${this.base}/goals/era`);
  }

  goalsCorr() {
    return this.http.get<any>(`${this.base}/goals/correlation`);
  }

  hosts() {
    return this.http.get<any>(`${this.base}/hosts/metrics`);
  }

  resultsRunnerUps() {
    return this.http.get<any>(`${this.base}/results/runnerups`);
  }

  resultsThird() {
    return this.http.get<any>(`${this.base}/results/thirdplace`);
  }

  top3() {
    return this.http.get<any>(`${this.base}/top3/stats`);
  }

  top3Consistency() {
    return this.http.get<any>(`${this.base}/top3/consistency/top`);
  }

  structure() {
    return this.http.get<any>(`${this.base}/structure/combined`);
  }

  summary() {
    return this.http.get<any>(`${this.base}/summary`);
  }
}
