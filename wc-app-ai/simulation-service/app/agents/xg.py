import random


class XGAgent:

    def generate_xg(
            self,
            attack_strength: int,
            defense_strength: int
    ) -> float:

        base_xg = random.uniform(0.05, 0.35)

        strength_factor = (
            attack_strength
            /
            max(defense_strength, 1)
        )

        xg = base_xg * strength_factor

        return round(
            min(max(xg, 0.02), 0.90),
            2
        )