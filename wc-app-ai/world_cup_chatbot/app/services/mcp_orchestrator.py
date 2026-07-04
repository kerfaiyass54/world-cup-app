from app.services.history_agent import (
    HistoryAgent
)

from app.services.statistics_agent import (
    StatisticsAgent
)

from app.services.gemini_service import (
    GeminiService
)

from app.services.analysis_agent import (
    AnalysisAgent
)


class MCPOrchestrator:

    def __init__(self):

        self.history_agent = HistoryAgent()

        self.statistics_agent = StatisticsAgent()

        self.gemini = GeminiService()

        self.analysis_agent = AnalysisAgent()

    async def process(
        self,
        question: str,
        memory: str
    ) -> str:

        q = question.lower()

        if any(
            word in q
            for word in [
                "winner",
                "champion",
                "world cup",
                "history"
            ]
        ):

            answer = await (
                self.history_agent.answer(
                    question
                )
            )

            if (
                "couldn't find"
                not in answer.lower()
            ):
                return answer

        if any(
            word in q
            for word in [
                "goal",
                "goals",
                "scorer",
                "stats"
            ]
        ):

            answer = await (
                self.statistics_agent.answer(
                    question
                )
            )

            if (
                "no statistics"
                not in answer.lower()
            ):
                return answer
        if "winner" in q and "all" in q:
            data = (
                await self.analysis_agent
                .world_cup_winners()
            )

            return str(data)
        if "top scorers" in q:
            data = (
                await self.analysis_agent
                .top_scorers()
            )

            return str(data)
        if "brazil" in q:
            data = (
                await self.analysis_agent
                .country_titles(
                    "Brazil"
                )
            )

            return str(data)

        return await (
            self.gemini.ask_with_memory(
                memory
            )
        )