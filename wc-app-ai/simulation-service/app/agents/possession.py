import random


class PossessionAgent:

    def decide_possession(
            self,
            home_power: int,
            away_power: int
    ) -> bool:

        total = home_power + away_power

        if total <= 0:
            return random.choice([True, False])

        home_probability = (
            home_power / total
        )

        return (
            random.random()
            < home_probability
        )