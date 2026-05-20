from pipeline.preprocessing import split_top_scorer


def best_top_scorer_record(df):

    df = split_top_scorer(df.copy())

    row = df.loc[df["TopScorerGoals"].idxmax()]

    return {
        "Year": row["Year"],
        "Player": row["TopScorerPlayer"],
        "Goals": row["TopScorerGoals"]
    }


def top_scorer_goals_by_year(df):

    df = split_top_scorer(df.copy())

    return df[[
        "Year",
        "TopScorerPlayer",
        "TopScorerGoals"
    ]]


def country_with_most_top_scorers(df):

    df = split_top_scorer(df.copy())

    return df["TopScorerPlayer"].value_counts()


def most_frequent_top_scorer(df):

    df = split_top_scorer(df.copy())

    return df["TopScorerPlayer"].value_counts().idxmax()