class FormationAgent:

    FORMATIONS = {

        "4-3-3": {
            "attack": 10,
            "possession": 5
        },

        "4-4-2": {
            "attack": 0,
            "possession": 0
        },

        "4-2-3-1": {
            "attack": 5,
            "possession": 10
        },

        "3-5-2": {
            "attack": 8,
            "possession": 12
        },

        "5-3-2": {
            "attack": -10,
            "possession": -5
        }
    }

    def formation_modifier(
            self,
            formation: str
    ):

        return (
            self.FORMATIONS
            .get(
                formation,
                {}
            )
            .get(
                "attack",
                0
            )
        )

    def possession_modifier(
            self,
            formation: str
    ):

        return (
            self.FORMATIONS
            .get(
                formation,
                {}
            )
            .get(
                "possession",
                0
            )
        )