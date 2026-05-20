from pipeline.preprocessing import split_top_scorer


def yearly_summary(df):

    df = split_top_scorer(df.copy())

    return df[[
        "Year",
        "Host",
        "Champion",
        "Runner-Up",
        "Matches",
        "TopScorerPlayer",
        "TopScorerGoals"
    ]]