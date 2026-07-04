from app.services.data_loader import (
    world_cup_data
)


class HistoryAgent:

    async def answer(
        self,
        question: str
    ) -> str:

        question = question.lower()

        world_cups = (
            world_cup_data.get_world_cups()
        )

        if "winner" in question:

            for _, row in world_cups.iterrows():

                year = str(row.iloc[0])

                if year in question:

                    return (
                        f"The winner of the "
                        f"{year} FIFA World Cup "
                        f"was {row.iloc[2]}."
                    )

        return (
            "I couldn't find the answer "
            "in historical records."
        )