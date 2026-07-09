import random


class ShotAgent:

    def shot_on_target(
            self,
            attacking_power: int
    ) -> bool:

        probability = min(
            max(
                attacking_power / 180,
                0.20
            ),
            0.85
        )

        return (
            random.random()
            < probability
        )