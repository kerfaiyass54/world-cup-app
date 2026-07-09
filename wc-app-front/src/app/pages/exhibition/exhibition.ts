import {
  Component,
  OnInit,
  inject,
  ChangeDetectorRef
} from '@angular/core';

import {
  CommonModule
} from '@angular/common';

import {
  Router,
  RouterLink
} from '@angular/router';

import {
  TournamentService
} from '../../tournament.service';

@Component({
  selector: 'app-exhibition',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
  ],
  templateUrl: './exhibition.html',
  styleUrl: './exhibition.css'
})
export class Exhibition
  implements OnInit {

  private tournamentService =
    inject(TournamentService);

  private router =
    inject(Router);

  private cdr =
    inject(ChangeDetectorRef);

  tournaments: any[] = [];

  loading = false;

  ngOnInit(): void {

    this.loadTournaments();
  }

  loadTournaments(): void {

    const email =
      sessionStorage.getItem(
        'email'
      );

    if (!email) {

      this.tournaments = [];

      this.cdr.detectChanges();

      return;
    }

    this.loading = true;

    this.cdr.detectChanges();

    this.tournamentService
      .getByEmail(email)
      .subscribe({

        next: (
          response: any[]
        ) => {

          this.tournaments =
            [...(response || [])];

          this.loading = false;

          this.cdr.detectChanges();
        },

        error: (error) => {

          console.error(error);

          this.tournaments = [];

          this.loading = false;

          this.cdr.detectChanges();
        }
      });
  }

  createTournament(): void {

    this.router.navigate([
      '/exhibition/create'
    ]);
  }

  openTournament(
    tournament: any
  ): void {

    this.router.navigate([
      '/exhibition',
      tournament.id
    ]);
  }

  refresh(): void {

    this.loadTournaments();
  }

  trackTournament(
    index: number,
    tournament: any
  ): string {

    return tournament.id;
  }
}
