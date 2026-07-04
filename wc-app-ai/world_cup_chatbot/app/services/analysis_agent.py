from app.services.data_loader import (
    world_cup_data
)


class AnalysisAgent:

    async def world_cup_winners(self):

        df = world_cup_data.get_world_cups()

        return df.to_dict(
            orient="records"
        )

    async def country_titles(
        self,
        country: str
    ):

        df = world_cup_data.get_world_cups()

        wins = df[
            df.apply(
                lambda row:
                country.lower()
                in str(row).lower(),
                axis=1
            )
        ]

        return wins.to_dict(
            orient="records"
        )

    async def top_scorers(
        self,
        limit: int = 10
    ):

        df = (
            world_cup_data
            .get_top_scorers()
        )

        return (
            df.head(limit)
            .to_dict(
                orient="records"
            )
        )