import random


class GoalAgent:

    def is_goal(
            self,
            xg: float
    ) -> bool:

        return random.random() < xg