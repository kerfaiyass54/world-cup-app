import pandas as pd


def split_top_scorer(df):


    split_cols = df["TopScorrer"].str.rsplit("-", n=1, expand=True)

    df["TopScorerPlayer"] = split_cols[0].str.strip()
    df["TopScorerGoals"] = split_cols[1].astype(int)

    return df