import pandas as pd


def calculate_team_statistics(df):

    teams = []

    for _, row in df.iterrows():

        teams.append({
            "team": row["home_team"],
            "opponent": row["away_team"],
            "goals_scored": row["home_score"],
            "goals_conceded": row["away_score"],
            "attendance": row["Attendance"],
            "year": row["Year"],
            "result":
                "Win"
                if row["home_score"] > row["away_score"]
                else (
                    "Loss"
                    if row["home_score"] < row["away_score"]
                    else "Draw"
                )
        })

        teams.append({
            "team": row["away_team"],
            "opponent": row["home_team"],
            "goals_scored": row["away_score"],
            "goals_conceded": row["home_score"],
            "attendance": row["Attendance"],
            "year": row["Year"],
            "result":
                "Win"
                if row["away_score"] > row["home_score"]
                else (
                    "Loss"
                    if row["away_score"] < row["home_score"]
                    else "Draw"
                )
        })

    team_df = pd.DataFrame(teams)

    stats = (
        team_df
        .groupby("team")
        .agg(
            matches_played=("team", "count"),

            wins=(
                "result",
                lambda x: (x == "Win").sum()
            ),

            draws=(
                "result",
                lambda x: (x == "Draw").sum()
            ),

            losses=(
                "result",
                lambda x: (x == "Loss").sum()
            ),

            goals_scored=(
                "goals_scored",
                "sum"
            ),

            goals_conceded=(
                "goals_conceded",
                "sum"
            ),

            avg_attendance=(
                "attendance",
                "mean"
            ),

            appearances=(
                "year",
                "nunique"
            )
        )
        .reset_index()
    )

    stats["goal_difference"] = (
        stats["goals_scored"]
        - stats["goals_conceded"]
    )

    stats["win_percentage"] = (
        stats["wins"]
        / stats["matches_played"]
        * 100
    ).round(2)

    return stats