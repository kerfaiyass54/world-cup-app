class HomeTeamAgent:

    def calculate_power(
            self,
            ability: int,
            morale: str
    ):

        morale_bonus = {
            "GOOD": 10,
            "AVERAGE": 0,
            "BAD": -10
        }

        return ability + morale_bonus.get(
            morale.upper(),
            0
        )