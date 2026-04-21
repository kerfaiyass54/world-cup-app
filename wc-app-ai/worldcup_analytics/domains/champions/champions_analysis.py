def champion_counts(df):

    return df["CHAMPION"].value_counts()


def champion_by_era(df):

    return df.groupby(
        ["ERA", "CHAMPION"]
    ).size()


def champion_streaks(df):

    df_sorted = df.sort_values("YEAR")

    return (
        df_sorted["CHAMPION"]
        .groupby(
            (
                df_sorted["CHAMPION"]
                != df_sorted["CHAMPION"].shift()
            ).cumsum()
        )
        .transform("size")
    )