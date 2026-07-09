import random


class ChanceAgent:

    def create_chance(
            self,
            attacking_power: int,
            defending_power: int
    ) -> bool:

        probability = (
            attacking_power
            /
            (
                attacking_power
                + defending_power
            )
        )

        probability *= 0.60

        return (
            random.random()
            < probability
        )