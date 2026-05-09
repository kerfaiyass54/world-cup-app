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

  championsEra: any[] = [];
  goalsCorr: any;

  runnerUps: any[] = [];
  thirdPlace: any[] = [];

  visible: boolean[] = [];

  constructor(private api: ApiService) {}

  ngOnInit(): void {

    this.api.summary().subscribe(res => {
      this.summary = res;
    });

    this.visible = [
      true,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false
    ];

    // SUMMARY



    // CHAMPIONS

    this.api.champions().subscribe(res => {

      this.champions = res.slice(0, 8);

      setTimeout(() => this.buildChampionsChart(), 0);
    });

    // TOP3

    this.api.top3().subscribe(res => {

      this.top3 = res.slice(0, 8);

      setTimeout(() => this.buildTop3Chart(), 0);
    });

    // GOALS

    this.api.goalsEra().subscribe(res => {

      this.goalsEra = res;

      setTimeout(() => this.buildGoalsChart(), 0);
    });

    // HOSTS

    this.api.hosts().subscribe(res => {
      this.hosts = res;
    });

    // STRUCTURE

    this.api.structure().subscribe(res => {

      this.structure = res;

      setTimeout(() => this.buildStructureChart(), 0);
    });

    // ERA

    this.api.championsEra().subscribe(res => {

      this.championsEra = res;

      setTimeout(() => this.buildEraChart(), 0);
    });

    // RUNNER UPS

    this.api.resultsRunnerUps().subscribe(res => {

      this.runnerUps = res.slice(0, 10);

      setTimeout(() => this.buildRunnerChart(), 0);
    });

    // THIRD PLACE

    this.api.resultsThird().subscribe(res => {

      this.thirdPlace = res.slice(0, 10);

      setTimeout(() => this.buildThirdChart(), 0);
    });

    // CORRELATION

    this.api.goalsCorr().subscribe(res => {
      this.goalsCorr = res;
    });

    setTimeout(() => {
      this.onScroll();
    }, 200);

  }

  // SCROLL

  @HostListener('window:scroll', [])
  @HostListener('window:scroll', [])
  onScroll() {

    const sections = document.querySelectorAll('.section');

    sections.forEach((sec, i) => {

      const rect = sec.getBoundingClientRect();

      if (rect.top < window.innerHeight) {

        setTimeout(() => {

          this.visible[i] = true;

        }, i * 120);
      }
    });
  }

  // COMMON OPTIONS

  options() {

    return {

      responsive: true,

      maintainAspectRatio: false,

      plugins: {

        legend: {

          labels: {

            color: 'white',

            font: {
              size: 14
            }
          }
        }
      },

      scales: {

        x: {

          ticks: {
            color: 'white'
          },

          grid: {
            color: 'rgba(255,255,255,0.05)'
          }
        },

        y: {

          ticks: {
            color: 'white'
          },

          grid: {
            color: 'rgba(255,255,255,0.05)'
          }
        }
      }
    };
  }

  // CHAMPIONS

  buildChampionsChart() {

    new Chart('championsChart', {

      type: 'bar',

      data: {

        labels: this.champions.map(x => x.country),

        datasets: [{

          label: 'Wins',

          data: this.champions.map(x => x.wins),

          borderRadius: 15,

          backgroundColor: [
            '#8b5cf6',
            '#7c3aed',
            '#6d28d9',
            '#9333ea',
            '#2563eb',
            '#0ea5e9',
            '#06b6d4',
            '#6366f1'
          ]
        }]
      },

      options: this.options()
    });
  }

  // TOP3

  buildTop3Chart() {

    new Chart('top3Chart', {

      type: 'polarArea',

      data: {

        labels: this.top3.map(x => x.country),

        datasets: [{

          data: this.top3.map(x => x.appearances),

          backgroundColor: [
            '#8b5cf6',
            '#7c3aed',
            '#9333ea',
            '#2563eb',
            '#06b6d4',
            '#3b82f6',
            '#c084fc',
            '#6366f1'
          ]
        }]
      },

      options: this.options()
    });
  }

  // GOALS

  buildGoalsChart() {

    new Chart('goalsChart', {

      type: 'line',

      data: {

        labels: this.goalsEra.map(x => x.era),

        datasets: [{

          label: 'Goals/Game',

          data: this.goalsEra.map(x => x.avgGoalsPerGame),

          borderColor: '#a855f7',

          backgroundColor: 'rgba(168,85,247,0.2)',

          fill: true,

          tension: 0.4,

          pointRadius: 6
        }]
      },

      options: this.options()
    });
  }

  // STRUCTURE

  buildStructureChart() {

    new Chart('structureChart', {

      type: 'line',

      data: {

        labels: this.structure.map(x => x.year),

        datasets: [

          {
            label: 'Teams',

            data: this.structure.map(x => x.teams),

            borderColor: '#8b5cf6',

            tension: 0.4,

            pointRadius: 3
          },

          {
            label: 'Matches',

            data: this.structure.map(x => x.matches),

            borderColor: '#06b6d4',

            tension: 0.4,

            pointRadius: 3
          }
        ]
      },

      options: this.options()
    });
  }

  // ERA

  buildEraChart() {

    new Chart('eraChart', {

      type: 'doughnut',

      data: {

        labels: this.championsEra.map(x => x.era),

        datasets: [{

          data: this.championsEra.map(x => x.maxWins),

          backgroundColor: [
            '#8b5cf6',
            '#06b6d4',
            '#2563eb'
          ]
        }]
      },

      options: this.options()
    });
  }

  // RUNNER UPS

  buildRunnerChart() {

    new Chart('runnerChart', {

      type: 'bar',

      data: {

        labels: this.runnerUps.map(x => x.country),

        datasets: [{

          label: 'Runner-Ups',

          data: this.runnerUps.map(x => x.runnerupCount),

          borderRadius: 10,

          backgroundColor: '#f59e0b'
        }]
      },

      options: this.options()
    });
  }

  // THIRD PLACE

  buildThirdChart() {

    new Chart('thirdChart', {

      type: 'bar',

      data: {

        labels: this.thirdPlace.map(x => x.country),

        datasets: [{

          label: 'Third Place Finishes',

          data: this.thirdPlace.map(x => x.thirdplaceCount),

          borderRadius: 12,

          borderSkipped: false,

          backgroundColor: [
            '#f59e0b',
            '#fbbf24',
            '#fcd34d',
            '#fde68a',
            '#f59e0b',
            '#fbbf24',
            '#fcd34d',
            '#fde68a',
            '#f59e0b',
            '#fbbf24'
          ]
        }]
      },

      options: {

        indexAxis: 'y',

        responsive: true,

        maintainAspectRatio: false,

        plugins: {

          legend: {

            labels: {

              color: 'white',

              font: {
                size: 14
              }
            }
          }
        },

        scales: {

          x: {

            beginAtZero: true,

            ticks: {
              color: 'white'
            },

            grid: {
              color: 'rgba(255,255,255,0.05)'
            }
          },

          y: {

            ticks: {
              color: 'white'
            },

            grid: {
              display: false
            }
          }
        }
      }
    });
  }

}
