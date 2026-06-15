def preprocess_data(df):

    df = df.copy()

    df["goal_margin"] = (
        df["home_score"]
        - df["away_score"]
    )

    def get_result(x):

        if x > 0:
            return "Home Win"

        if x < 0:
            return "Away Win"

        return "Draw"

    df["result"] = (
        df["goal_margin"]
        .apply(get_result)
    )

    return df