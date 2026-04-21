def runnerup_counts(df):

    return df["RUNNER UP"].value_counts()


def runnerup_without_titles(df):

    winners = set(df["CHAMPION"])

    runnerups = set(df["RUNNER UP"])

    return runnerups - winners