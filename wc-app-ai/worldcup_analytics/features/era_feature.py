import numpy as np

def add_era(df):
    df["ERA"] = np.select(
        [
            df["YEAR"] < 1950,
            (df["YEAR"] >= 1950) & (df["YEAR"] <= 1990),
            df["YEAR"] > 1990
        ],
        [
            "Early",
            "Mid",
            "Modern"
        ],
        default="Unknown"  # <-- fix
    )
    return df