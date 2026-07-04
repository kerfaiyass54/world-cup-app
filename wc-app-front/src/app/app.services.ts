import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ApiService {

  base = 'http://localhost:9040/api';

  constructor(private http: HttpClient) {}

  // =========================================
  // EXISTING APIs
  // =========================================

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

  structure() {
    return this.http.get<any>(`${this.base}/structure/combined`);
  }

  summary() {
    return this.http.get<any>(`${this.base}/summary`);
  }

  // =========================================
  // YEARLY SUMMARY
  // =========================================

  yearlySummary() {
    return this.http.get<any[]>(
      `${this.base}/yearly-summary`
    );
  }

  yearlySummaryNames() {
    return this.http.get<any>(
      `${this.base}/yearly-summary/names`
    );
  }

  yearlySummaryByYear(year: number) {
    return this.http.get<any>(
      `${this.base}/yearly-summary/${year}`
    );
  }

  // =========================================
  // HIGHLIGHTS
  // =========================================

  highestAttendance() {
    return this.http.get<any>(
      `${this.base}/highlights/highest-attendance`
    );
  }

  highestAvgAttendance() {
    return this.http.get<any>(
      `${this.base}/highlights/highest-avg-attendance`
    );
  }

  bestTopScorer() {
    return this.http.get<any>(
      `${this.base}/highlights/best-top-scorer`
    );
  }

  // =========================================
  // ATTENDANCE BY HOST
  // =========================================

  attendanceByHost() {
    return this.http.get<any>(
      `${this.base}/attendance/host`
    );
  }

  avgAttendanceByHost() {
    return this.http.get<any>(
      `${this.base}/attendance/host/avg`
    );
  }

  // =========================================
  // ATTENDANCE BY YEAR
  // =========================================

  attendanceByYear() {
    return this.http.get<any>(
      `${this.base}/attendance/year`
    );
  }

  avgAttendanceByYear() {
    return this.http.get<any>(
      `${this.base}/attendance/year/avg`
    );
  }

  // =========================================
  // TOP SCORERS
  // =========================================

  topScorers() {
    return this.http.get<any>(
      `${this.base}/top-scorers`
    );
  }

  topScorerTitles() {
    return this.http.get<any>(
      `${this.base}/top-scorers/titles`
    );
  }

 statsGetAll(){
    return this.http.get<any>(`${this.base}/stats`);
 }

}
