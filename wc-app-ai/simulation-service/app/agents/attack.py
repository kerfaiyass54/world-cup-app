import random


class AttackAgent:

    def create_attack(
            self,
            attacking_power: int
    ) -> bool:

        probability = min(
            max(
                attacking_power / 200,
                0.15
            ),
            0.80
        )

        return (
            random.random()
            < probability
        )