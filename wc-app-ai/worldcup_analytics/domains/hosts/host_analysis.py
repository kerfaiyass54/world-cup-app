def host_top3_count(df):

    return df["HOST_IN_TOP3"].sum()


def host_final_count(df):

    return df["HOST_IN_FINAL"].sum()


def host_champion_count(df):

    return df["HOST_IS_CHAMPION"].sum()


def host_advantage_ratio(df):

    return df["HOST_IN_TOP3"].mean()