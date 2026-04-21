import pandas as pd


def top3_counts(df):

    top3 = pd.concat([
        df["CHAMPION"],
        df["RUNNER UP"],
        df["THIRD PLACE"]
    ])

    return top3.value_counts()


def top3_unique(df):

    top3 = pd.concat([
        df["CHAMPION"],
        df["RUNNER UP"],
        df["THIRD PLACE"]
    ])

    return top3.nunique()


def consistency_score(df):

    counts = top3_counts(df)

    return counts / len(df)