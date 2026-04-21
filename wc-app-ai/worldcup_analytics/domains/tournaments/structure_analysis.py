def teams_growth(df):

    return df[["YEAR", "TEAMS"]]


def matches_growth(df):

    return df[["YEAR", "MATCHES PLAYED"]]


def structure_by_era(df):

    return df.groupby("ERA").agg({

        "TEAMS": "mean",
        "MATCHES PLAYED": "mean",
        "GOALS SCORED": "mean"

    })