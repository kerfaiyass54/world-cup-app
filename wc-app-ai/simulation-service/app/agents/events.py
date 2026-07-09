import random


class EventAgent:

    EVENTS = [
        "SHOT",
        "FOUL",
        "CORNER",
        "SAVE",
        "GOAL"
    ]

    def generate(self):

        return random.choice(
            self.EVENTS
        )