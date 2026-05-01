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
    host_advantage_ratio
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


# =========================
# Kafka Producer
# =========================
producer = KafkaProducer(
    bootstrap_servers="wc_kafka:29093",
    value_serializer=lambda v: json.dumps(v).encode("utf-8")
)


# =========================
# Helpers
# =========================
def to_json_safe(data):
    """
    Converts pandas objects into JSON-safe structures
    """

    # DataFrame
    if hasattr(data, "to_dict"):
        try:
            return data.to_dict(orient="records")
        except Exception:
            return data.reset_index().to_dict(orient="records")

    # Fallback
    return data


def send(topic, payload):
    producer.send(topic, to_json_safe(payload))


# =========================
# Main Logic
# =========================
def produce_analytics():

    df = load_summary()
    df = add_era(df)
    df = add_host_features(df)

    # champions
    send(
        "worldcup.analytics.champions",
        champion_counts(df)
    )

    # champions by era
    send(
        "worldcup.analytics.champions_by_era",
        champion_by_era(df)
    )

    # runnerups
    send(
        "worldcup.analytics.runnerups",
        runnerup_counts(df)
    )

    # runnerups without titles
    send(
        "worldcup.analytics.runnerups_without_titles",
        list(runnerup_without_titles(df))
    )

    # thirdplace
    send(
        "worldcup.analytics.thirdplace",
        thirdplace_counts(df)
    )

    # top3
    send(
        "worldcup.analytics.top3",
        top3_counts(df)
    )

    send(
        "worldcup.analytics.top3_consistency",
        consistency_score(df)
    )

    # host metrics
    send(
        "worldcup.analytics.host_metrics",
        {
            "host_top3_count": int(host_top3_count(df)),
            "host_final_count": int(host_final_count(df)),
            "host_champion_count": int(host_champion_count(df)),
            "host_top3_ratio": float(host_advantage_ratio(df))
        }
    )

    # scoring analytics
    send(
        "worldcup.analytics.scoring_corr",
        {
            "teams_scoring_corr": float(teams_vs_scoring_corr(df))
        }
    )

    send(
        "worldcup.analytics.scoring_by_era",
        scoring_by_era(df)
    )

    # structure analytics
    send(
        "worldcup.analytics.structure_by_era",
        structure_by_era(df)
    )

    send(
        "worldcup.analytics.teams_growth",
        teams_growth(df)
    )

    send(
        "worldcup.analytics.matches_growth",
        matches_growth(df)
    )

    producer.flush()
    print("ALL analytics streamed successfully")


# =========================
# Entry Point
# =========================
if __name__ == "__main__":
    produce_analytics()