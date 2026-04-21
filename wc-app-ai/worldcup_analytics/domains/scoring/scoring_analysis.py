def teams_vs_scoring_corr(df):

    return df["TEAMS"].corr(
        df["AVG GOALS PER GAME"]
    )


def scoring_by_era(df):

    return df.groupby("ERA")[
        "AVG GOALS PER GAME"
    ].mean()


def goals_trend(df):

    return df[[
        "YEAR",
        "AVG GOALS PER GAME"
    ]]