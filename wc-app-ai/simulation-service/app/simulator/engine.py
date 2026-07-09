from app.agents.director import (
    MatchDirector
)

from app.kafka.producer import (
    send_result
)


class SimulationEngine:

    def __init__(self):

        self.director = MatchDirector()

    async def simulate(
            self,
            request
    ):

        result = (
            await self.director
            .simulate_match(
                request
            )
        )

        await send_result(
            result
        )

        return result


engine = SimulationEngine()