import random


class RefereeAgent:

    def is_foul(self):

        return random.random() < 0.08

    def yellow_card(self):

        return random.random() < 0.03

    def red_card(self):

        return random.random() < 0.005