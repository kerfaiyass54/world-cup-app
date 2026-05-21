import {
  Component,
  OnInit,
  AfterViewInit,
  ChangeDetectionStrategy
} from '@angular/core';

import { CommonModule } from '@angular/common';

import { ApiService } from '../../../app.services';

import {
  Chart,
  CategoryScale,
  LinearScale,
  BarController,
  BarElement,
  LineController,
  LineElement,
  PointElement,
  ArcElement,
  DoughnutController,
  Tooltip,
  Legend,
  Filler
} from 'chart.js';

Chart.register(
    CategoryScale,
    LinearScale,
    BarController,
    BarElement,
    LineController,
    LineElement,
    PointElement,
    ArcElement,
    DoughnutController,
    Tooltip,
    Legend,
    Filler
);

@Component({
  selector: 'app-tournaments-infos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './tournaments-infos.html',
  styleUrl: './tournaments-infos.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TournamentsInfos
    implements OnInit, AfterViewInit {

  tournaments:any[] = [];

  highestAttendance:any;
  highestAvgAttendance:any;
  bestTopScorer:any;

  attendanceByYear:any[] = [];
  avgAttendanceByYear:any[] = [];

  attendanceByHost:any[] = [];
  avgAttendanceByHost:any[] = [];

  topScorerGoals:any[] = [];

  selectedTournament:any = null;

  showTimelineModal = false;
  showHighlightsModal = false;

  constructor(
      private api: ApiService
  ) {}

  ngOnInit(): void {

    this.loadData();
  }

  ngAfterViewInit(): void {

    this.initRevealAnimation();
  }

  /* =========================================
     MODALS
  ========================================== */

  openTimelineModal(): void {

    this.showTimelineModal = true;
  }

  closeTimelineModal(): void {

    this.showTimelineModal = false;
  }

  openHighlightsModal(): void {

    this.showHighlightsModal = true;
  }

  closeHighlightsModal(): void {

    this.showHighlightsModal = false;
  }

  openTournament(item:any): void {

    this.selectedTournament = item;
  }

  /* =========================================
     LOAD DATA
  ========================================== */

  loadData(): void {

    this.api.yearlySummary()
        .subscribe(res => {

          this.tournaments = res;
        });

    this.api.highestAttendance()
        .subscribe(res => {

          this.highestAttendance = res;
        });

    this.api.highestAvgAttendance()
        .subscribe(res => {

          this.highestAvgAttendance = res;
        });

    this.api.bestTopScorer()
        .subscribe(res => {

          this.bestTopScorer = res;
        });

    this.api.attendanceByYear()
        .subscribe(res => {

          this.attendanceByYear = res;

          setTimeout(() => {
            this.attendanceYearChart();
          });
        });

    this.api.avgAttendanceByYear()
        .subscribe(res => {

          this.avgAttendanceByYear = res;

          setTimeout(() => {
            this.avgAttendanceYearChart();
          });
        });

    this.api.attendanceByHost()
        .subscribe(res => {

          this.attendanceByHost = res;

          setTimeout(() => {
            this.attendanceHostChart();
          });
        });

    this.api.avgAttendanceByHost()
        .subscribe(res => {

          this.avgAttendanceByHost = res;

          setTimeout(() => {
            this.avgAttendanceHostChart();
          });
        });

    this.api.topScorers()
        .subscribe(res => {

          this.topScorerGoals = res;

          setTimeout(() => {
            this.topScorerChart();
          });
        });
  }

  /* =========================================
     REVEAL
  ========================================== */

  initRevealAnimation(): void {

    const reveals =
        document.querySelectorAll('.reveal');

    const observer =
        new IntersectionObserver(

            entries => {

              entries.forEach(entry => {

                if(entry.isIntersecting){

                  entry.target.classList.add('active');

                  observer.unobserve(entry.target);
                }
              });

            },

            {
              threshold:0.15
            }
        );

    reveals.forEach(el => {

      observer.observe(el);
    });
  }

  /* =========================================
     CHARTS
  ========================================== */

  attendanceYearChart(): void {

    const ctx:any =
        document.getElementById(
            'attendanceYearChart'
        );

    if(!ctx) return;

    new Chart(ctx, {

      type:'line',

      data:{

        labels:
            this.attendanceByYear.map(x => x.year),

        datasets:[
          {
            label:'Attendance',

            data:
                this.attendanceByYear.map(
                    x => x.attendance
                ),

            borderColor:'#22d3ee',

            backgroundColor:
                'rgba(34,211,238,.15)',

            fill:true,

            tension:.4
          }
        ]
      },

      options:this.chartStyle()
    });
  }

  avgAttendanceYearChart(): void {

    const ctx:any =
        document.getElementById(
            'avgAttendanceYearChart'
        );

    if(!ctx) return;

    new Chart(ctx, {

      type:'bar',

      data:{

        labels:
            this.avgAttendanceByYear.map(
                x => x.year
            ),

        datasets:[
          {
            label:'Average Attendance',

            data:
                this.avgAttendanceByYear.map(
                    x => x.attendanceAvg
                ),

            backgroundColor:'#8b5cf6',

            borderRadius:12
          }
        ]
      },

      options:this.chartStyle()
    });
  }

  attendanceHostChart(): void {

    const ctx:any =
        document.getElementById(
            'attendanceHostChart'
        );

    if(!ctx) return;

    new Chart(ctx, {

      type:'doughnut',

      data:{

        labels:
            this.attendanceByHost.map(
                x => x.host
            ),

        datasets:[
          {
            data:
                this.attendanceByHost.map(
                    x => x.attendance
                ),

            backgroundColor:[
              '#22d3ee',
              '#8b5cf6',
              '#ef4444',
              '#22c55e',
              '#f59e0b',
              '#ec4899'
            ]
          }
        ]
      },

      options:{
        responsive:true,
        maintainAspectRatio:false,

        plugins:{
          legend:{
            labels:{
              color:'#fff'
            }
          }
        }
      }
    });
  }

  avgAttendanceHostChart(): void {

    const ctx:any =
        document.getElementById(
            'avgAttendanceHostChart'
        );

    if(!ctx) return;

    new Chart(ctx, {

      type:'line',

      data:{

        labels:
            this.avgAttendanceByHost.map(
                x => x.host
            ),

        datasets:[
          {
            label:'Average Attendance',

            data:
                this.avgAttendanceByHost.map(
                    x => x.attendanceAvg
                ),

            borderColor:'#22c55e',

            backgroundColor:
                'rgba(34,197,94,.15)',

            fill:true,

            tension:.4
          }
        ]
      },

      options:this.chartStyle()
    });
  }

  topScorerChart(): void {

    const ctx:any =
        document.getElementById(
            'topScorerChart'
        );

    if(!ctx) return;

    new Chart(ctx, {

      type:'bar',

      data:{

        labels:
            this.topScorerGoals.map(
                x =>
                    `${x.topScorerPlayer} (${x.year})`
            ),

        datasets:[
          {
            label:'Goals',

            data:
                this.topScorerGoals.map(
                    x => x.topScorerGoals
                ),

            backgroundColor:'#ef4444',

            borderRadius:12
          }
        ]
      },

      options:this.chartStyle()
    });
  }

  chartStyle(): any {

    return {

      responsive:true,

      maintainAspectRatio:false,

      plugins:{

        legend:{
          labels:{
            color:'#fff'
          }
        }
      },

      scales:{

        x:{

          ticks:{
            color:'#cbd5e1'
          },

          grid:{
            color:'rgba(255,255,255,.05)'
          }
        },

        y:{

          ticks:{
            color:'#cbd5e1'
          },

          grid:{
            color:'rgba(255,255,255,.05)'
          }
        }
      }
    };
  }
}
