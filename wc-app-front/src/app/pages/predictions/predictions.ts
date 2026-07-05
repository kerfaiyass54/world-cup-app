import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {PredictionService} from '../../prediction.service';


interface Match {
  home: string;
  away: string;
  homeScore: number | null;
  awayScore: number | null;
}

interface Group {
  name: string;
  teams: string[];
  matches: Match[];
}

interface Standing {
  group?: string;
  team: string;
  played: number;
  pts: number;
  gf: number;
  ga: number;
  gd: number;
}

interface KnockoutMatch {
  id: number;

  homeTeam: string;
  awayTeam: string;

  homeScore: number | null;
  awayScore: number | null;
}



@Component({
  selector: 'app-predictions',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './predictions.html',
  styleUrl: './predictions.css',
})
export class Predictions {


  flagMap: Record<string, string> = {

    'USA': 'us',
    'Canada': 'ca',
    'Mexico': 'mx',

    'France': 'fr',
    'Germany': 'de',
    'Spain': 'es',
    'Portugal': 'pt',
    'Belgium': 'be',
    'Croatia': 'hr',
    'Netherlands': 'nl',
    'Sweden': 'se',
    'Norway': 'no',
    'Austria': 'at',
    'Scotland': 'gb-sct',
    'England': 'gb-eng',

    'Brazil': 'br',
    'Argentina': 'ar',
    'Uruguay': 'uy',
    'Colombia': 'co',
    'Paraguay': 'py',

    'Morocco': 'ma',
    'Senegal': 'sn',
    'Egypt': 'eg',
    'Algeria': 'dz',
    'Tunisia': 'tn',
    'Ghana': 'gh',
    'Ivory Coast': 'ci',
    'South Africa': 'za',
    'Cape Verde': 'cv',

    'Japan': 'jp',
    'South Korea': 'kr',
    'Saudi Arabia': 'sa',
    'Iran': 'ir',
    'Iraq': 'iq',
    'Jordan': 'jo',

    'Qatar': 'qa',
    'Switzerland': 'ch',
    'Bosnia': 'ba',
    'Turkiye': 'tr',
    'Australia': 'au',
    'Ecuador': 'ec',
    'Panama': 'pa',
    'DR Congo': 'cd',
    'Uzbekistan': 'uz',

    'Haiti': 'ht',
    'Czechia': 'cz',
    'New Zealand': 'nz',
    'Curacao': 'cw'
  };

  quarterFinals: KnockoutMatch[] = [];

  semiFinals: KnockoutMatch[] = [];

  thirdPlace: KnockoutMatch[] = [];

  finalMatch: KnockoutMatch[] = [];

  champion = '';
  runnerUp = '';
  thirdPlaceWinner = '';

  generateQuarterFinals() {

    this.quarterFinals = [

      {
        id: 97,
        homeTeam: this.getWinner(this.round16[0]),
        awayTeam: this.getWinner(this.round16[1]),
        homeScore: null,
        awayScore: null,
      },

      {
        id: 98,
        homeTeam: this.getWinner(this.round16[2]),
        awayTeam: this.getWinner(this.round16[3]),
        homeScore: null,
        awayScore: null,
      },

      {
        id: 99,
        homeTeam: this.getWinner(this.round16[4]),
        awayTeam: this.getWinner(this.round16[5]),
        homeScore: null,
        awayScore: null,
      },

      {
        id: 100,
        homeTeam: this.getWinner(this.round16[6]),
        awayTeam: this.getWinner(this.round16[7]),
        homeScore: null,
        awayScore: null,
      }

    ];

  }

  generateSemiFinals() {

    this.semiFinals = [

      {
        id: 101,
        homeTeam: this.getWinner(this.quarterFinals[0]),
        awayTeam: this.getWinner(this.quarterFinals[1]),
        homeScore: null,
        awayScore: null,
      },

      {
        id: 102,
        homeTeam: this.getWinner(this.quarterFinals[2]),
        awayTeam: this.getWinner(this.quarterFinals[3]),
        homeScore: null,
        awayScore: null,
      }

    ];

  }

  getLoser(match: KnockoutMatch): string {

    if (
      match.homeScore === null ||
      match.awayScore === null
    ) {
      return 'TBD';
    }

    return match.homeScore > match.awayScore
      ? match.awayTeam
      : match.homeTeam;
  }


  generateFinals() {

    this.thirdPlace = [

      {
        id: 103,
        homeTeam: this.getLoser(this.semiFinals[0]),
        awayTeam: this.getLoser(this.semiFinals[1]),
        homeScore: null,
        awayScore: null,
      }

    ];

    this.finalMatch = [

      {
        id: 104,
        homeTeam: this.getWinner(this.semiFinals[0]),
        awayTeam: this.getWinner(this.semiFinals[1]),
        homeScore: null,
        awayScore: null,
      }

    ];

  }

  generateChampion() {

    const final = this.finalMatch[0];

    if (
      final.homeScore === null ||
      final.awayScore === null
    ) {
      return;
    }

    if (final.homeScore > final.awayScore) {

      this.champion = final.homeTeam;
      this.runnerUp = final.awayTeam;

    } else {

      this.champion = final.awayTeam;
      this.runnerUp = final.homeTeam;

    }

    const third = this.thirdPlace[0];

    if (
      third.homeScore !== null &&
      third.awayScore !== null
    ) {

      this.thirdPlaceWinner =
        third.homeScore > third.awayScore
          ? third.homeTeam
          : third.awayTeam;

    }

  }



  round16: KnockoutMatch[] = [];

  currentStep = 0;

  selectedGroup = 0;

  steps = [
    'Groups',
    'Round of 32',
    'Round of 16',
    'Quarterfinals',
    'Semifinals',
    'Final',
    'Champion',
  ];

  round32: KnockoutMatch[] = [];

  groups: Group[] = [
    {
      name: 'A',
      teams: ['Mexico', 'South Africa', 'South Korea', 'Czechia'],
      matches: [],
    },
    {
      name: 'B',
      teams: ['Canada', 'Bosnia', 'Qatar', 'Switzerland'],
      matches: [],
    },
    {
      name: 'C',
      teams: ['Brazil', 'Morocco', 'Haiti', 'Scotland'],
      matches: [],
    },
    {
      name: 'D',
      teams: ['USA', 'Paraguay', 'Australia', 'Turkiye'],
      matches: [],
    },
    {
      name: 'E',
      teams: ['Germany', 'Curacao', 'Ivory Coast', 'Ecuador'],
      matches: [],
    },
    {
      name: 'F',
      teams: ['Netherlands', 'Japan', 'Sweden', 'Tunisia'],
      matches: [],
    },
    {
      name: 'G',
      teams: ['Belgium', 'Egypt', 'Iran', 'New Zealand'],
      matches: [],
    },
    {
      name: 'H',
      teams: ['Spain', 'Cape Verde', 'Saudi Arabia', 'Uruguay'],
      matches: [],
    },
    {
      name: 'I',
      teams: ['France', 'Senegal', 'Iraq', 'Norway'],
      matches: [],
    },
    {
      name: 'J',
      teams: ['Argentina', 'Algeria', 'Austria', 'Jordan'],
      matches: [],
    },
    {
      name: 'K',
      teams: ['Colombia', 'DR Congo', 'Portugal', 'Uzbekistan'],
      matches: [],
    },
    {
      name: 'L',
      teams: ['England', 'Croatia', 'Ghana', 'Panama'],
      matches: [],
    },
  ];

  constructor(private predictionService:
              PredictionService) {
    this.groups.forEach(group => {
      group.matches =
        this.generateMatches(group.teams);
    });

    this.loadPrediction();
  }



  exportJson() {

    const data = {

      groups: this.groups,

      round32: this.round32,

      round16: this.round16,

      quarterFinals: this.quarterFinals,

      semiFinals: this.semiFinals,

      thirdPlace: this.thirdPlace,

      finalMatch: this.finalMatch,

      champion: this.champion,

      runnerUp: this.runnerUp
    };

    const blob = new Blob(
      [JSON.stringify(data, null, 2)],
      {
        type: 'application/json'
      }
    );

    const url =
      window.URL.createObjectURL(blob);

    const a =
      document.createElement('a');

    a.href = url;

    a.download =
      'world-cup-prediction.json';

    a.click();

    URL.revokeObjectURL(url);
  }

  exportCsv() {

    let csv =
      'Stage,Home,Away,HomeScore,AwayScore\n';

    const rounds = [

      ...this.round32,
      ...this.round16,
      ...this.quarterFinals,
      ...this.semiFinals,
      ...this.thirdPlace,
      ...this.finalMatch
    ];

    rounds.forEach(match => {

      csv += `${match.id},${match.homeTeam},${match.awayTeam},${match.homeScore ?? ''},${match.awayScore ?? ''}\n`;

    });

    const blob =
      new Blob([csv], {
        type: 'text/csv'
      });

    const url =
      URL.createObjectURL(blob);

    const a =
      document.createElement('a');

    a.href = url;

    a.download =
      'world-cup-prediction.csv';

    a.click();

    URL.revokeObjectURL(url);
  }

  email = '';

  generateMatches(teams: string[]): Match[] {
    return [
      {
        home: teams[0],
        away: teams[1],
        homeScore: null,
        awayScore: null,
      },
      {
        home: teams[2],
        away: teams[3],
        homeScore: null,
        awayScore: null,
      },
      {
        home: teams[0],
        away: teams[2],
        homeScore: null,
        awayScore: null,
      },
      {
        home: teams[1],
        away: teams[3],
        homeScore: null,
        awayScore: null,
      },
      {
        home: teams[0],
        away: teams[3],
        homeScore: null,
        awayScore: null,
      },
      {
        home: teams[1],
        away: teams[2],
        homeScore: null,
        awayScore: null,
      },
    ];
  }

  selectGroup(index: number) {
    this.selectedGroup = index;
  }

  getStandings(group: Group): Standing[] {
    const table: Record<string, Standing> = {};

    group.teams.forEach((team) => {
      table[team] = {
        team,
        played: 0,
        pts: 0,
        gf: 0,
        ga: 0,
        gd: 0,
      };
    });

    group.matches.forEach((match) => {
      if (
        match.homeScore === null ||
        match.awayScore === null
      ) {
        return;
      }

      const home = table[match.home];
      const away = table[match.away];

      home.played++;
      away.played++;

      home.gf += match.homeScore;
      home.ga += match.awayScore;

      away.gf += match.awayScore;
      away.ga += match.homeScore;

      if (match.homeScore > match.awayScore) {
        home.pts += 3;
      } else if (match.homeScore < match.awayScore) {
        away.pts += 3;
      } else {
        home.pts++;
        away.pts++;
      }
    });

    Object.values(table).forEach((team) => {
      team.gd = team.gf - team.ga;
    });

    return Object.values(table).sort(
      (a, b) =>
        b.pts - a.pts ||
        b.gd - a.gd ||
        b.gf - a.gf
    );
  }

  getBestThirdPlaced(): Standing[] {
    const thirds: Standing[] = [];

    this.groups.forEach((group) => {
      const table = this.getStandings(group);

      thirds.push({
        ...table[2],
        group: group.name,
      });
    });

    return thirds
      .sort(
        (a, b) =>
          b.pts - a.pts ||
          b.gd - a.gd ||
          b.gf - a.gf
      )
      .slice(0, 8);
  }

  allocateThirdPlaces(
    qualifiedGroups: string[]
  ) {

    const allowedSources: Record<number, string[]> = {
      74: ['A', 'B', 'F', 'G', 'H'],
      77: ['C', 'D', 'F', 'G', 'H'],
      79: ['C', 'E', 'F', 'H', 'I'],
      80: ['E', 'H', 'I', 'J', 'K'],
      81: ['A', 'E', 'H', 'I', 'J'],
      82: ['B', 'E', 'F', 'I', 'J', 'L'],
      85: ['A', 'B', 'C', 'F', 'G'],
      87: ['E', 'F', 'G', 'I', 'J'],
    };

    const pool = [...qualifiedGroups].sort();

    const allocation: Record<number, string> = {};

    const order = [
      80,
      82,
      74,
      77,
      79,
      81,
      85,
      87,
    ];

    for (const match of order) {

      for (const group of [...pool]) {

        if (
          allowedSources[match].includes(group)
        ) {
          allocation[match] = group;

          pool.splice(
            pool.indexOf(group),
            1
          );

          break;
        }
      }
    }

    return allocation;
  }

  generateRound32() {

    const winners: Record<string, string> = {};
    const runners: Record<string, string> = {};

    this.groups.forEach((group) => {
      const standings =
        this.getStandings(group);

      winners[group.name] =
        standings[0].team;

      runners[group.name] =
        standings[1].team;
    });

    const thirds =
      this.getBestThirdPlaced();

    const wildcardMap =
      this.allocateThirdPlaces(
        thirds.map((x) => x.group!)
      );

    const getThird = (
      groupLetter: string
    ) =>
      thirds.find(
        (x) =>
          x.group === groupLetter
      )?.team ?? 'TBD';

    this.round32 = [

      {
        id: 73,
        homeTeam: runners['A'],
        awayTeam: runners['B'],
        homeScore: null,
        awayScore: null,
      },

      {
        id: 74,
        homeTeam: winners['C'],
        awayTeam: getThird(
          wildcardMap[74]
        ),
        homeScore: null,
        awayScore: null,
      },

      {
        id: 75,
        homeTeam: winners['F'],
        awayTeam: runners['C'],
        homeScore: null,
        awayScore: null,
      },

      {
        id: 76,
        homeTeam: winners['C'],
        awayTeam: runners['F'],
        homeScore: null,
        awayScore: null,
      },

      {
        id: 77,
        homeTeam: winners['I'],
        awayTeam: getThird(
          wildcardMap[77]
        ),
        homeScore: null,
        awayScore: null,
      },

      {
        id: 78,
        homeTeam: runners['E'],
        awayTeam: runners['I'],
        homeScore: null,
        awayScore: null,
      },

      {
        id: 79,
        homeTeam: winners['A'],
        awayTeam: getThird(
          wildcardMap[79]
        ),
        homeScore: null,
        awayScore: null,
      },

      {
        id: 80,
        homeTeam: winners['L'],
        awayTeam: getThird(
          wildcardMap[80]
        ),
        homeScore: null,
        awayScore: null,
      },

      {
        id: 81,
        homeTeam: winners['G'],
        awayTeam: getThird(
          wildcardMap[81]
        ),
        homeScore: null,
        awayScore: null,
      },

      {
        id: 82,
        homeTeam: winners['D'],
        awayTeam: getThird(
          wildcardMap[82]
        ),
        homeScore: null,
        awayScore: null,
      },

      {
        id: 83,
        homeTeam: runners['K'],
        awayTeam: runners['L'],
        homeScore: null,
        awayScore: null,
      },

      {
        id: 84,
        homeTeam: winners['H'],
        awayTeam: runners['J'],
        homeScore: null,
        awayScore: null,
      },

      {
        id: 85,
        homeTeam: winners['H'],
        awayTeam: getThird(
          wildcardMap[85]
        ),
        homeScore: null,
        awayScore: null,
      },

      {
        id: 86,
        homeTeam: winners['J'],
        awayTeam: runners['H'],
        homeScore: null,
        awayScore: null,
      },

      {
        id: 87,
        homeTeam: winners['B'],
        awayTeam: getThird(
          wildcardMap[87]
        ),
        homeScore: null,
        awayScore: null,
      },

      {
        id: 88,
        homeTeam: runners['D'],
        awayTeam: runners['G'],
        homeScore: null,
        awayScore: null,
      },
    ];
  }

  nextStep() {

    if (
      this.currentStep === 0 &&
      this.round32.length === 0
    ) {
      this.generateRound32();
    }

    else if (
      this.currentStep === 1 &&
      this.round16.length === 0
    ) {
      this.generateRound16();
    }

    else if (
      this.currentStep === 2 &&
      this.quarterFinals.length === 0
    ) {
      this.generateQuarterFinals();
    }

    else if (
      this.currentStep === 3 &&
      this.semiFinals.length === 0
    ) {
      this.generateSemiFinals();
    }

    else if (
      this.currentStep === 4 &&
      this.finalMatch.length === 0
    ) {
      this.generateFinals();
    }

    else if (
      this.currentStep === 5
    ) {
      this.generateChampion();
    }

    this.currentStep++;

  }



  loadPrediction() {

    const saved =
      localStorage.getItem('wc2026-prediction');

    if (!saved) return;

    const data = JSON.parse(saved);

    this.groups = data.groups;
    this.round32 = data.round32;
    this.round16 = data.round16;
    this.quarterFinals = data.quarterFinals;
    this.semiFinals = data.semiFinals;
    this.finalMatch = data.finalMatch;
    this.thirdPlace = data.thirdPlace;
    this.champion = data.champion;
  }

  previousStep() {
    this.currentStep--;
  }

  getWinner(match: KnockoutMatch): string {

    if (
      match.homeScore === null ||
      match.awayScore === null
    ) {
      return 'TBD';
    }

    return match.homeScore > match.awayScore
      ? match.homeTeam
      : match.awayTeam;
  }

  generateRound16() {

    if (this.round32.length === 0) {
      return;
    }

    this.round16 = [

      {
        id: 89,
        homeTeam: this.getWinner(this.round32[0]),
        awayTeam: this.getWinner(this.round32[1]),
        homeScore: null,
        awayScore: null,
      },

      {
        id: 90,
        homeTeam: this.getWinner(this.round32[2]),
        awayTeam: this.getWinner(this.round32[3]),
        homeScore: null,
        awayScore: null,
      },

      {
        id: 91,
        homeTeam: this.getWinner(this.round32[4]),
        awayTeam: this.getWinner(this.round32[5]),
        homeScore: null,
        awayScore: null,
      },

      {
        id: 92,
        homeTeam: this.getWinner(this.round32[6]),
        awayTeam: this.getWinner(this.round32[7]),
        homeScore: null,
        awayScore: null,
      },

      {
        id: 93,
        homeTeam: this.getWinner(this.round32[8]),
        awayTeam: this.getWinner(this.round32[9]),
        homeScore: null,
        awayScore: null,
      },

      {
        id: 94,
        homeTeam: this.getWinner(this.round32[10]),
        awayTeam: this.getWinner(this.round32[11]),
        homeScore: null,
        awayScore: null,
      },

      {
        id: 95,
        homeTeam: this.getWinner(this.round32[12]),
        awayTeam: this.getWinner(this.round32[13]),
        homeScore: null,
        awayScore: null,
      },

      {
        id: 96,
        homeTeam: this.getWinner(this.round32[14]),
        awayTeam: this.getWinner(this.round32[15]),
        homeScore: null,
        awayScore: null,
      },

    ];
  }

  savePrediction() {

    const prediction = {

      email: this.email,

      champion: this.champion,

      thirdPlace: this.thirdPlaceWinner,

      groupsJson: JSON.stringify(
        this.groups
      ),

      bracketJson: JSON.stringify({

        round32: this.round32,

        round16: this.round16,

        quarterFinals: this.quarterFinals,

        semiFinals: this.semiFinals,

        final: this.finalMatch
      })
    };

    this.predictionService
      .savePrediction(prediction)
      .subscribe({

        next: (response) => {

          console.log(
            'Prediction saved',
            response
          );

          alert(
            'Prediction saved successfully'
          );
        },

        error: (error) => {

          console.error(error);

          alert(
            'Failed to save prediction'
          );
        }
      });
  }


}
