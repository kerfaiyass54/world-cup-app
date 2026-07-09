import {
  Component,
  OnInit,
  inject,
  ChangeDetectorRef,
  ElementRef,
  ViewChild
} from '@angular/core';

import {
  CommonModule,
  Location
} from '@angular/common';

import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import {
  TournamentService
} from '../../../tournament.service';

import {
  TeamService
} from '../../../team.service';

@Component({
  selector: 'app-tournament-details',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './tournament-details.html',
  styleUrl: './tournament-details.css',
})
export class TournamentDetails implements OnInit {
  private route = inject(ActivatedRoute);

  private tournamentService = inject(TournamentService);

  private teamService = inject(TeamService);

  private location = inject(Location);

  private cdr = inject(ChangeDetectorRef);

  private router = inject(Router);

  tournamentId: string = '';

  viewStandings(groupId: string): void {
    this.router.navigate([
      '/exhibition',
      'tournaments',
      this.tournamentId,
      'groups',
      groupId,
      'standings',
    ]);
  }

  @ViewChild('carouselContainer')
  carouselContainer!: ElementRef;

  tournament: any = null;

  teams: any[] = [];

  teamsMap: Record<string, any> = {};

  loading = true;

  flagMap: Record<string, string> = {
    Argentina: 'ar',
    France: 'fr',
    Spain: 'es',
    England: 'gb-eng',
    Brazil: 'br',
    Portugal: 'pt',
    Germany: 'de',
    Netherlands: 'nl',
    Belgium: 'be',
    Croatia: 'hr',
    Uruguay: 'uy',
    Colombia: 'co',
    Morocco: 'ma',
    Japan: 'jp',
    Switzerland: 'ch',
    Austria: 'at',
    Norway: 'no',
    Mexico: 'mx',
    USA: 'us',
    Türkiye: 'tr',
    Sweden: 'se',
    Senegal: 'sn',
    Iran: 'ir',
    Ecuador: 'ec',
    Paraguay: 'py',
    Egypt: 'eg',
    Canada: 'ca',
    'South Korea': 'kr',
    Algeria: 'dz',
    'Ivory Coast': 'ci',
    Scotland: 'gb-sct',
    Tunisia: 'tn',
    Ghana: 'gh',
    Czechia: 'cz',
    'Bosnia and Herzegovina': 'ba',
    Australia: 'au',
    'Saudi Arabia': 'sa',
    Panama: 'pa',
    Jordan: 'jo',
    Iraq: 'iq',
    Uzbekistan: 'uz',
    'South Africa': 'za',
    'Cape Verde': 'cv',
    'DR Congo': 'cd',
    Qatar: 'qa',
    Haiti: 'ht',
    Curacao: 'cw',
    'New Zealand': 'nz',
  };

  ngOnInit(): void {
    this.tournamentId = this.route.snapshot.params['id'];

    this.loadTeams();
  }

  private refreshView(): void {
    this.cdr.detectChanges();
  }

  viewBracket(): void {
    this.router.navigate(['/', 'tournaments', this.tournamentId, 'bracket']);
  }

  loadTeams(): void {
    this.teamService.getAll().subscribe({
      next: (response: any) => {
        this.teams = response || [];

        this.teams.forEach((team) => {
          this.teamsMap[team.id] = team;
        });

        this.loadTournament();

        this.refreshView();
      },

      error: (error) => {
        console.error(error);

        this.loadTournament();
      },
    });
  }

  loadTournament(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (!id) {
      this.loading = false;

      return;
    }

    this.tournamentService.getTournament(id).subscribe({
      next: (response: any) => {
        this.tournament = response;

        this.loading = false;

        this.refreshView();
      },

      error: (error) => {
        console.error(error);

        this.loading = false;

        this.refreshView();
      },
    });
  }

  goBack(): void {
    this.location.back();
  }

  scrollLeft(): void {
    this.carouselContainer?.nativeElement?.scrollBy({
      left: -500,

      behavior: 'smooth',
    });
  }

  scrollRight(): void {
    this.carouselContainer?.nativeElement?.scrollBy({
      left: 500,

      behavior: 'smooth',
    });
  }

  getTeam(teamId: string): any {
    return this.teamsMap[teamId];
  }

  getTeamName(teamId: string): string {
    return this.getTeam(teamId)?.name || 'Unknown';
  }

  getFlag(teamId: string): string {
    const team = this.getTeam(teamId);

    if (!team) {
      return 'https://flagcdn.com/w40/un.png';
    }

    const code = this.flagMap[team.name];

    return code ? `https://flagcdn.com/w40/${code}.png` : 'https://flagcdn.com/w40/un.png';
  }

  getAverageAbility(group: any): number {
    if (!group?.teamIds?.length) {
      return 0;
    }

    const total = group.teamIds.reduce(
      (sum: number, teamId: string) => {
        return sum + (this.getTeam(teamId)?.ability || 0);
      },

      0,
    );

    return Math.round(total / group.teamIds.length);
  }

  getTotalTeams(): number {
    if (!this.tournament) {
      return 0;
    }

    return this.tournament.groups.reduce(
      (total: number, group: any) => total + group.teamIds.length,

      0,
    );
  }

  openGroup(groupId: string): void {
    this.router.navigate(['/', this.tournament.id, 'groups', groupId]);
  }
}
