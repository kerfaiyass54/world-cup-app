import pandas as pd


class WorldCupDataService:

    def __init__(self):

        self.world_cups = pd.read_csv(
            "app/datasets/history/world_cup.csv"
        )

        self.matches = pd.read_csv(
            "app/datasets/history/matches_1930_2022.csv"
        )

        self.top_scorers = pd.read_csv(
            "app/datasets/history/top_scorers.csv"
        )

        self.players = pd.read_csv(
            "app/datasets/players/players.csv"
        )

        self.teams = pd.read_csv(
            "app/datasets/players/teams.csv"
        )

        self.goals = pd.read_csv(
            "app/datasets/players/goals.csv"
        )

    def get_world_cups(self):
        return self.world_cups

    def get_matches(self):
        return self.matches

    def get_players(self):
        return self.players

    def get_teams(self):
        return self.teams

    def get_goals(self):
        return self.goals

    def get_top_scorers(self):
        return self.top_scorers