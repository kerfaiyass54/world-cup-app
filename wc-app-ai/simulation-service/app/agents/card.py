import random


class CardAgent:

    def yellow_card(self):

        return random.random() < 0.30

    def red_card(self):

        return random.random() < 0.03