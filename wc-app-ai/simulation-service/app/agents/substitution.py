import random


class SubstitutionAgent:

    def should_substitute(
            self,
            minute
    ):

        if minute < 55:
            return False

        return random.random() < 0.08