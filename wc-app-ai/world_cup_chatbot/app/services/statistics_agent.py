from app.services.data_loader import (
    world_cup_data
)


class StatisticsAgent:

    async def answer(
        self,
        question: str
    ) -> str:

        question = question.lower()

        scorers = (
            world_cup_data.get_top_scorers()
        )

        if "top scorer" in question:

            latest = scorers.iloc[-1]

            return (
                f"The latest World Cup top scorer "
                f"is {latest.iloc[1]}"
            )

        return (
            "No statistics found."
        )