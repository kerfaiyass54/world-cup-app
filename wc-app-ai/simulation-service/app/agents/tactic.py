class TacticAgent:

    ATTACK_MODIFIERS = {

        "DEFENSIVE": -15,

        "BALANCED": 0,

        "ATTACKING": 15
    }

    POSSESSION_MODIFIERS = {

        "DEFENSIVE": -5,

        "BALANCED": 0,

        "ATTACKING": 5
    }

    def attack_bonus(
            self,
            tactic: str
    ):

        return self.ATTACK_MODIFIERS.get(
            tactic.upper(),
            0
        )

    def possession_bonus(
            self,
            tactic: str
    ):

        return self.POSSESSION_MODIFIERS.get(
            tactic.upper(),
            0
        )