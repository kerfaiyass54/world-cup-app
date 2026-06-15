import {
  Component,
  OnInit,
  AfterViewInit
} from '@angular/core';

import { CommonModule } from '@angular/common';

import {
  Chart,
  registerables
} from 'chart.js';
import { ApiService } from '../../../app.services';

Chart.register(...registerables);

@Component({
  selector: 'app-teams-infos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './teams-infos.html',
  styleUrl: './teams-infos.css'
})
export class TeamsInfos
  implements OnInit, AfterViewInit {

  teams: any[] = [];


  constructor(
    private api: ApiService
  ) {}

  ngOnInit(): void {

    this.api
      .statsGetAll()
      .subscribe(data => {

        this.teams = data;

        setTimeout(() => {
          this.buildCharts();
          this.observeSections();
        }, 100);
      });
  }

  ngAfterViewInit() {}

  observeSections() {

    const observer =
      new IntersectionObserver(
        entries => {

          entries.forEach(entry => {

            if (
              entry.isIntersecting
            ) {
              entry.target.classList.add(
                'visible'
              );
            }

          });

        },
        {
          threshold: 0.15
        }
      );

    document
      .querySelectorAll('.reveal')
      .forEach(section =>
        observer.observe(section)
      );
  }

  createChart(
    id: string,
    label: string,
    data: any[],
    key: string
  ) {

    const ctx =
      document.getElementById(
        id
      ) as HTMLCanvasElement;

    if (!ctx) return;

    new Chart(ctx, {

      type: 'bar',

      data: {

        labels:
          data.map(
            t => t.team
          ),

        datasets: [
          {
            label,
            data:
              data.map(
                t => t[key]
              ),
            borderRadius: 12,

            backgroundColor: [
              '#60A5FA',
              '#34D399',
              '#F59E0B',
              '#F472B6',
              '#A78BFA',
              '#22D3EE',
              '#FB7185',
              '#4ADE80',
              '#E879F9',
              '#FBBF24'
            ],

            borderColor: '#ffffff',

            borderWidth: 1,
            hoverBackgroundColor: '#ffffff',
            hoverBorderWidth: 3

          }
        ]
      },

      options: {

        responsive: true,

        indexAxis: 'y',

        plugins: {
          legend: {
            display: false
          }
        },

        scales: {

          x: {
            ticks: {
              color: '#fff'
            },
            grid: {
              color:
                'rgba(255,255,255,.08)'
            }
          },

          y: {
            ticks: {
              color: '#fff'
            },
            grid: {
              display: false
            }
          }
        }
      }
    });
  }

  buildCharts() {

    const appearances =
      [...this.teams]
        .sort(
          (a, b) =>
            b.appearances -
            a.appearances
        );

    this.createChart(
      'appearancesChart',
      'Appearances',
      appearances,
      'appearances'
    );

    const goals =
      [...this.teams]
        .sort(
          (a, b) =>
            b.goalsScored -
            a.goalsScored
        );

    this.createChart(
      'goalsChart',
      'Goals',
      goals,
      'goalsScored'
    );

    const conceded =
      [...this.teams]
        .sort(
          (a, b) =>
            b.goalsConceded -
            a.goalsConceded
        );

    this.createChart(
      'concededChart',
      'Goals Conceded',
      conceded,
      'goalsConceded'
    );

    const wins =
      [...this.teams]
        .sort(
          (a, b) =>
            b.wins - a.wins
        );

    this.createChart(
      'winsChart',
      'Wins',
      wins,
      'wins'
    );

    const losses =
      [...this.teams]
        .sort(
          (a, b) =>
            b.losses -
            a.losses
        );

    this.createChart(
      'lossesChart',
      'Losses',
      losses,
      'losses'
    );

    const top3 =
      [...this.teams]
        .sort(
          (a, b) =>
            b.winPercentage -
            a.winPercentage
        )
        .slice(0, 3);

    this.createChart(
      'winChart',
      'Win %',
      top3,
      'winPercentage'
    );

    const scorers =
      [...this.teams]
        .sort(
          (a, b) =>
            b.goalsScored -
            a.goalsScored
        )
        .slice(0, 3);

    this.createChart(
      'scorersChart',
      'Goals',
      scorers,
      'goalsScored'
    );
  }
}
