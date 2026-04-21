from kafka import KafkaProducer
import json

from loader.csv_loader import load_summary
from features.era_feature import add_era
from features.host_feature import add_host_features

from domains.champions.champions_analysis import (
    champion_counts,
    champion_by_era
)

from domains.runnerups.runnerup_analysis import (
    runnerup_counts,
    runnerup_without_titles
)

from domains.thirdplace.thirdplace_analysis import (
    thirdplace_counts
)

from domains.top3.top3_analysis import (
    top3_counts,
    consistency_score
)

from domains.hosts.host_analysis import (
    host_top3_count,
    host_final_count,
    host_champion_count,
    host_advantage_ratio,
    host_top3_by_country
)

from domains.scoring.scoring_analysis import (
    teams_vs_scoring_corr,
    scoring_by_era
)

from domains.tournaments.structure_analysis import (
    structure_by_era,
    teams_growth,
    matches_growth
)


producer = KafkaProducer(
    bootstrap_servers="localhost:9093",
    value_serializer=lambda v: json.dumps(v).encode("utf-8")
)


def send(topic, payload):

    producer.send(topic, payload)


def produce_analytics():

    df = load_summary()

    df = add_era(df)

    df = add_host_features(df)


    # champions
    send(
        "worldcup.analytics.champions",
        champion_counts(df).to_dict()
    )


    # champions by era
    send(
        "worldcup.analytics.champions_by_era",
        champion_by_era(df).to_dict()
    )


    # runnerups
    send(
        "worldcup.analytics.runnerups",
        runnerup_counts(df).to_dict()
    )


    # runnerups without titles
    send(
        "worldcup.analytics.runnerups_without_titles",
        list(runnerup_without_titles(df))
    )


    # thirdplace
    send(
        "worldcup.analytics.thirdplace",
        thirdplace_counts(df).to_dict()
    )


    # top3
    send(
        "worldcup.analytics.top3",
        top3_counts(df).to_dict()
    )


    send(
        "worldcup.analytics.top3_consistency",
        consistency_score(df).to_dict()
    )


    # host metrics
    send(
        "worldcup.analytics.host_metrics",
        {

            "host_top3_count":
                int(host_top3_count(df)),

            "host_final_count":
                int(host_final_count(df)),

            "host_champion_count":
                int(host_champion_count(df)),

            "host_top3_ratio":
                float(host_advantage_ratio(df))
        }
    )


    send(
        "worldcup.analytics.host_top3_by_country",
        host_top3_by_country(df).to_dict()
    )


    # scoring analytics
    send(
        "worldcup.analytics.scoring_corr",
        {
            "teams_scoring_corr":
                float(teams_vs_scoring_corr(df))
        }
    )


    send(
        "worldcup.analytics.scoring_by_era",
        scoring_by_era(df).to_dict()
    )


    # structure analytics
    send(
        "worldcup.analytics.structure_by_era",
        structure_by_era(df).to_dict()
    )


    send(
        "worldcup.analytics.teams_growth",
        teams_growth(df).to_dict("records")
    )


    send(
        "worldcup.analytics.matches_growth",
        matches_growth(df).to_dict("records")
    )


    producer.flush()

    print("ALL analytics streamed successfully")


if __name__ == "__main__":

    produce_analytics()