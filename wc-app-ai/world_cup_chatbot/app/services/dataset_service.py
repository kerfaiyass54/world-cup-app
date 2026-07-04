from pathlib import Path

import pandas as pd


class DatasetService:

    def __init__(self):

        base_path = Path("app/datasets")

        self.world_cup = pd.read_csv(
            base_path / "history" / "world_cup.csv"
        )

        self.matches_history = pd.read_csv(
            base_path / "history" / "matches_1930_2022.csv"
        )

        self.top_scorers = pd.read_csv(
            base_path / "history" / "top_scorers.csv"
        )

        self.rankings = pd.read_csv(
            base_path / "history" / "fifa_ranking_2022.csv"
        )

        self.players = pd.read_csv(
            base_path / "players" / "players.csv"
        )

        self.goals = pd.read_csv(
            base_path / "players" / "goals.csv"
        )

        self.managers = pd.read_csv(
            base_path / "players" / "managers.csv"
        )

        self.referees = pd.read_csv(
            base_path / "players" / "referees.csv"
        )

        self.teams = pd.read_csv(
            base_path / "players" / "teams.csv"
        )

        self.matches_analysis = pd.read_csv(
            base_path / "analysis" / "matches.csv"
        )

        self.stadiums = pd.read_csv(
            base_path / "analysis" / "stadiums.csv"
        )

    def get_world_cups(self):

        return self.world_cup.to_dict(
            orient="records"
        )

    def get_top_scorers(self):

        return self.top_scorers.to_dict(
            orient="records"
        )

    def get_players(self):

        return self.players.to_dict(
            orient="records"
        )

    def get_teams(self):

        return self.teams.to_dict(
            orient="records"
        )