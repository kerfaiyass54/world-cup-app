import { Component, OnInit, HostListener } from '@angular/core';
import Chart from 'chart.js/auto';
import { ApiService } from '../../../app.services';

@Component({
  selector: 'app-general-info',
  standalone: true,
  templateUrl: './general-info.html',
  styleUrl: './general-info.css',
})
export class GeneralInfo implements OnInit {

  summary: any;
  champions: any[] = [];
  top3: any[] = [];
  goalsEra: any[] = [];
  structure: any[] = [];
  hosts: any;

  visible: boolean[] = [];

  constructor(private api: ApiService) {}

  ngOnInit(): void {

    this.visible = [false, false, false, false, false, false];

    this.api.summary().subscribe(res => this.summary = res);

    this.api.champions().subscribe(res => {
      this.champions = res.slice(0, 8);
      setTimeout(() => this.buildChampionsChart(), 0);
    });

    this.api.top3().subscribe(res => {
      this.top3 = res.slice(0, 8);
      setTimeout(() => this.buildTop3Chart(), 0);
    });

    this.api.goalsEra().subscribe(res => {
      this.goalsEra = res;
      setTimeout(() => this.buildGoalsChart(), 0);
    });

    this.api.structure().subscribe(res => {
      this.structure = res;
      setTimeout(() => this.buildStructureChart(), 0);
    });

    this.api.hosts().subscribe(res => this.hosts = res);
  }

  // 🔥 SCROLL ANIMATION
  @HostListener('window:scroll', [])
  onScroll() {
    const sections = document.querySelectorAll('.section');

    sections.forEach((sec, i) => {
      const rect = sec.getBoundingClientRect();
      if (rect.top < window.innerHeight - 120) {
        this.visible[i] = true;
      }
    });
  }

  // =========================
  // CHARTS
  // =========================

  buildChampionsChart() {
    new Chart('championsChart', {
      type: 'bar',
      data: {
        labels: this.champions.map(x => x.country),
        datasets: [{
          label: 'Wins',
          data: this.champions.map(x => x.wins)
        }]
      }
    });
  }

  buildTop3Chart() {
    new Chart('top3Chart', {
      type: 'bar',
      data: {
        labels: this.top3.map(x => x.country),
        datasets: [{
          label: 'Top3 Appearances',
          data: this.top3.map(x => x.appearances)
        }]
      }
    });
  }

  buildGoalsChart() {
    new Chart('goalsChart', {
      type: 'line',
      data: {
        labels: this.goalsEra.map(x => x.era),
        datasets: [{
          label: 'Goals/Game',
          data: this.goalsEra.map(x => x.avgGoalsPerGame)
        }]
      }
    });
  }

  buildStructureChart() {
    new Chart('structureChart', {
      type: 'line',
      data: {
        labels: this.structure.map(x => x.year),
        datasets: [
          {
            label: 'Teams',
            data: this.structure.map(x => x.teams)
          },
          {
            label: 'Matches',
            data: this.structure.map(x => x.matches)
          }
        ]
      }
    });
  }
}
