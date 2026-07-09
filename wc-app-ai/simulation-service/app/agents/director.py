import random

from app.agents.attack import AttackAgent
from app.agents.card import CardAgent
from app.agents.chance import ChanceAgent
from app.agents.foul import FoulAgent
from app.agents.goal import GoalAgent
from app.agents.injury import InjuryAgent
from app.agents.possession import PossessionAgent
from app.agents.shot import ShotAgent
from app.agents.substitution import SubstitutionAgent
from app.agents.xg import XGAgent
from app.agents.tactic import TacticAgent
from app.agents.formation import FormationAgent
from app.elastic.client import (
    save_event,
    save_match_stats
)

from app.models.event import MatchEvent
from app.models.match import MatchResult
from app.models.match_state import MatchState
from app.models.match_stats import MatchStats


class MatchDirector:

    def __init__(self):

        self.possession = PossessionAgent()
        self.attack = AttackAgent()
        self.chance = ChanceAgent()
        self.shot = ShotAgent()
        self.goal = GoalAgent()
        self.xg = XGAgent()

        self.tactic = TacticAgent()

        self.formation = FormationAgent()
        self.foul = FoulAgent()
        self.card = CardAgent()
        self.injury = InjuryAgent()
        self.substitution = SubstitutionAgent()

    async def simulate_match(
            self,
            request
    ):

        state = MatchState()

        morale_bonus = {
            "GOOD": 10,
            "AVERAGE": 0,
            "BAD": -10
        }

        home_power = (
                request.home_ability
                +
                morale_bonus.get(
                    request.home_morale.upper(),
                    0
                )
                +
                self.tactic.attack_bonus(
                    request.home_tactic
                )
                +
                self.formation.formation_modifier(
                    request.home_formation
                )
        )

        away_power = (
                request.away_ability
                +
                morale_bonus.get(
                    request.away_morale.upper(),
                    0
                )
                +
                self.tactic.attack_bonus(
                    request.away_tactic
                )
                +
                self.formation.formation_modifier(
                    request.away_formation
                )
        )

        await self.simulate_period(
            request,
            state,
            1,
            90,
            home_power,
            away_power
        )

        # Extra Time
        if (
                getattr(
                    request,
                    "is_knockout",
                    False
                )
                and
                state.home_goals
                ==
                state.away_goals
        ):

            state.extra_time = True

            await save_event(
                MatchEvent(
                    match_id=request.match_id,
                    minute=90,
                    event_type="EXTRA_TIME",
                    description="Extra Time Started"
                )
            )

            await self.simulate_period(
                request,
                state,
                91,
                120,
                home_power,
                away_power
            )

        # Penalties
        if (
                getattr(
                    request,
                    "is_knockout",
                    False
                )
                and
                state.home_goals
                ==
                state.away_goals
        ):

            state.penalties = True

            await save_event(
                MatchEvent(
                    match_id=request.match_id,
                    minute=120,
                    event_type="PENALTIES",
                    description="Penalty Shootout"
                )
            )

            await self.simulate_penalties(
                request,
                state
            )

        home_possession = round(
            (
                    state.home_possession_minutes
                    /
                    (
                            state.home_possession_minutes
                            +
                            state.away_possession_minutes
                    )
            )
            * 100
        )

        away_possession = (
                100 - home_possession
        )

        stats = MatchStats(
            match_id=request.match_id,

            home_goals=state.home_goals,
            away_goals=state.away_goals,

            home_shots=state.home_shots,
            away_shots=state.away_shots,

            home_shots_on_target=
            state.home_shots_on_target,

            away_shots_on_target=
            state.away_shots_on_target,

            home_yellow_cards=
            state.home_yellow_cards,

            away_yellow_cards=
            state.away_yellow_cards,

            home_red_cards=
            state.home_red_cards,

            away_red_cards=
            state.away_red_cards,

            home_possession=
            home_possession,

            away_possession=
            away_possession,

            home_xg=
            round(state.home_xg, 2),

            away_xg=
            round(state.away_xg, 2),

            home_fouls=
            state.home_fouls,

            away_fouls=
            state.away_fouls,

            home_substitutions=
            state.home_substitutions,

            away_substitutions=
            state.away_substitutions,

            home_injuries=
            state.home_injuries,

            away_injuries=
            state.away_injuries
        )

        await save_match_stats(
            stats
        )

        winner = None

        if state.home_goals > state.away_goals:
            winner = request.home_team_id

        elif state.away_goals > state.home_goals:
            winner = request.away_team_id

        elif state.penalties:

            if (
                    state.home_penalties
                    >
                    state.away_penalties
            ):
                winner = request.home_team_id
            else:
                winner = request.away_team_id

        return MatchResult(
            match_id=request.match_id,

            home_team_id=request.home_team_id,
            away_team_id=request.away_team_id,

            home_goals=state.home_goals,
            away_goals=state.away_goals,

            winner_team_id=winner,

            finished=True
        )

    async def simulate_period(
            self,
            request,
            state,
            start_minute,
            end_minute,
            home_power,
            away_power
    ):

        for minute in range(
                start_minute,
                end_minute + 1
        ):

            state.minute = minute

            state.home_fatigue += 1
            state.away_fatigue += 1

            effective_home_power = (
                    home_power
                    +
                    state.home_power_modifier
                    -
                    (state.home_fatigue // 20)
            )

            effective_away_power = (
                    away_power
                    +
                    state.away_power_modifier
                    -
                    (state.away_fatigue // 20)
            )

            home_has_ball = (
                self.possession.decide_possession(
                    effective_home_power,
                    effective_away_power
                )
            )

            if home_has_ball:
                state.home_possession_minutes += 1
            else:
                state.away_possession_minutes += 1

            await self.handle_match_events(
                request,
                state,
                minute,
                home_has_ball
            )

    async def handle_match_events(
            self,
            request,
            state,
            minute,
            home_has_ball
    ):

        # --------------------
        # Fouls
        # --------------------

        if self.foul.is_foul():

            if home_has_ball:
                state.away_fouls += 1
            else:
                state.home_fouls += 1

            await save_event(
                MatchEvent(
                    match_id=request.match_id,
                    minute=minute,
                    event_type="FOUL",
                    description="Foul committed"
                )
            )

        # --------------------
        # Yellow Cards
        # --------------------

        if self.card.yellow_card():

            if home_has_ball:
                state.away_yellow_cards += 1
            else:
                state.home_yellow_cards += 1

            await save_event(
                MatchEvent(
                    match_id=request.match_id,
                    minute=minute,
                    event_type="YELLOW_CARD",
                    description="Yellow Card"
                )
            )

        # --------------------
        # Red Cards
        # --------------------

        if self.card.red_card():

            if home_has_ball:

                state.away_red_cards += 1
                state.away_power_modifier -= 10

            else:

                state.home_red_cards += 1
                state.home_power_modifier -= 10

            await save_event(
                MatchEvent(
                    match_id=request.match_id,
                    minute=minute,
                    event_type="RED_CARD",
                    description="Red Card"
                )
            )

        # --------------------
        # Injuries
        # --------------------

        if self.injury.injured():

            if home_has_ball:
                state.home_injuries += 1
            else:
                state.away_injuries += 1

            await save_event(
                MatchEvent(
                    match_id=request.match_id,
                    minute=minute,
                    event_type="INJURY",
                    description="Player injured"
                )
            )

        # --------------------
        # Substitutions
        # --------------------

        if self.substitution.should_substitute(
                minute
        ):

            if home_has_ball:
                state.home_substitutions += 1
            else:
                state.away_substitutions += 1

            await save_event(
                MatchEvent(
                    match_id=request.match_id,
                    minute=minute,
                    event_type="SUBSTITUTION",
                    description="Substitution"
                )
            )

        # --------------------
        # Attacking / Defending Power
        # --------------------

        if home_has_ball:

            attacking_power = (
                    100
                    + state.home_power_modifier
            )

            defending_power = (
                    100
                    + state.away_power_modifier
            )

        else:

            attacking_power = (
                    100
                    + state.away_power_modifier
            )

            defending_power = (
                    100
                    + state.home_power_modifier
            )

        # --------------------
        # Attack
        # --------------------

        if not self.attack.create_attack(
                attacking_power
        ):
            return

        # --------------------
        # Chance
        # --------------------

        if not self.chance.create_chance(
                attacking_power,
                defending_power
        ):
            return

        attacking_home = home_has_ball

        # --------------------
        # Shot
        # --------------------

        if attacking_home:
            state.home_shots += 1
        else:
            state.away_shots += 1

        if not self.shot.shot_on_target(
                attacking_power
        ):
            await save_event(
                MatchEvent(
                    match_id=request.match_id,
                    minute=minute,
                    event_type="SHOT",
                    description="Shot Missed"
                )
            )

            return

        if attacking_home:
            state.home_shots_on_target += 1
        else:
            state.away_shots_on_target += 1

        # --------------------
        # xG
        # --------------------

        if attacking_home:

            xg = self.xg.generate_xg(
                attack_strength=attacking_power,
                defense_strength=defending_power
            )

            state.home_xg += xg

        else:

            xg = self.xg.generate_xg(
                attack_strength=attacking_power,
                defense_strength=defending_power
            )

            state.away_xg += xg

        # --------------------
        # Goal Check
        # --------------------

        if not self.goal.is_goal(xg):
            await save_event(
                MatchEvent(
                    match_id=request.match_id,
                    minute=minute,
                    event_type="SAVE",
                    description="Goalkeeper Save",
                    xg=xg
                )
            )

            return

        # --------------------
        # Goal
        # --------------------

        if attacking_home:

            state.home_goals += 1

            score = (
                f"{state.home_goals}"
                f"-"
                f"{state.away_goals}"
            )

            await save_event(
                MatchEvent(
                    match_id=request.match_id,
                    minute=minute,
                    event_type="GOAL",
                    team_id=request.home_team_id,
                    score=score,
                    xg=xg,
                    description=
                    f"{request.home_team_name} scored"
                )
            )

        else:

            state.away_goals += 1

            score = (
                f"{state.home_goals}"
                f"-"
                f"{state.away_goals}"
            )

            await save_event(
                MatchEvent(
                    match_id=request.match_id,
                    minute=minute,
                    event_type="GOAL",
                    team_id=request.away_team_id,
                    score=score,
                    xg=xg,
                    description=
                    f"{request.away_team_name} scored"
                )
            )

    async def simulate_penalties(
            self,
            request,
            state
    ):

        for attempt in range(1, 6):

            if random.random() < 0.75:

                state.home_penalties += 1

                await save_event(
                    MatchEvent(
                        match_id=request.match_id,
                        minute=120,
                        event_type="PENALTY_GOAL",
                        team_id=request.home_team_id,
                        description=
                        f"Penalty {attempt} scored"
                    )
                )

            else:

                await save_event(
                    MatchEvent(
                        match_id=request.match_id,
                        minute=120,
                        event_type="PENALTY_MISS",
                        team_id=request.home_team_id,
                        description=
                        f"Penalty {attempt} missed"
                    )
                )

            if random.random() < 0.75:

                state.away_penalties += 1

                await save_event(
                    MatchEvent(
                        match_id=request.match_id,
                        minute=120,
                        event_type="PENALTY_GOAL",
                        team_id=request.away_team_id,
                        description=
                        f"Penalty {attempt} scored"
                    )
                )

            else:

                await save_event(
                    MatchEvent(
                        match_id=request.match_id,
                        minute=120,
                        event_type="PENALTY_MISS",
                        team_id=request.away_team_id,
                        description=
                        f"Penalty {attempt} missed"
                    )
                )