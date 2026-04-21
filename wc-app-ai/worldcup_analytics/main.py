from loader.csv_loader import load_summary

from features.era_feature import add_era
from features.host_feature import add_host_features

from domains.top3.top3_analysis import top3_counts
from domains.hosts.host_analysis import host_advantage_ratio
from domains.scoring.scoring_analysis import teams_vs_scoring_corr



def main():

    df = load_summary()

    df = add_era(df)

    df = add_host_features(df)

    print(top3_counts(df))

    print(host_advantage_ratio(df))

    print(teams_vs_scoring_corr(df))


if __name__ == "__main__":

    main()