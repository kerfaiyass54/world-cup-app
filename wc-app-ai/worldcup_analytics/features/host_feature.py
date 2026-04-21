def add_host_features(df):

    df["HOST_IN_TOP3"] = (
        (df["HOST"] == df["CHAMPION"])
        | (df["HOST"] == df["RUNNER UP"])
        | (df["HOST"] == df["THIRD PLACE"])
    )

    df["HOST_IN_FINAL"] = (
        (df["HOST"] == df["CHAMPION"])
        | (df["HOST"] == df["RUNNER UP"])
    )

    df["HOST_IS_CHAMPION"] = (
        df["HOST"] == df["CHAMPION"]
    )

    return df