export interface Champion {
  country: string;
  wins: number;
}

export interface ChampionEra {
  era: string;
  maxWins: number;
  topCountries: string[];
}
