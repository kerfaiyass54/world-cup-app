import pandas as pd


def clean_data(df):

    df = df.copy()

    df = df.drop_duplicates()

    df["Date"] = pd.to_datetime(
        df["Date"],
        errors="coerce"
    )

    df["Attendance"] = (
        df["Attendance"]
        .fillna(0)
    )

    df = df.dropna(
        subset=[
            "home_team",
            "away_team",
            "home_score",
            "away_score"
        ]
    )

    return df