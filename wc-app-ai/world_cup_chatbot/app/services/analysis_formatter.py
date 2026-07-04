class AnalysisFormatter:

    @staticmethod
    def format_winners(
        winners
    ):

        result = (
            "World Cup Winners:\n\n"
        )

        for row in winners:

            result += (
                f"{row}\n"
            )

        return result

    @staticmethod
    def format_scorers(
        scorers
    ):

        result = (
            "Top Scorers:\n\n"
        )

        for row in scorers:

            result += (
                f"{row}\n"
            )

        return result