import json
from kafka import KafkaProducer

from pipeline.yearly_summary import yearly_summary
from pipeline.attendance import (
    highest_attendance,
    highest_average_attendance,
    attendance_by_year,
    average_attendance_by_year,
    attendance_by_host,
    average_attendance_by_host
)

from pipeline.topscorer import (
    best_top_scorer_record,
    top_scorer_goals_by_year,
    country_with_most_top_scorers,
    most_frequent_top_scorer
)

from pipeline.host_analysis import host_summary


# Kafka Producer
producer = KafkaProducer(
    bootstrap_servers="wc_kafka:29093",
    value_serializer=lambda v: json.dumps(v).encode("utf-8")
)


def send_to_kafka(topic, data):
    """
    Send data to Kafka topic
    """

    # Convert DataFrame -> dict
    if hasattr(data, "to_dict"):
        data = data.to_dict(orient="records")

    producer.send(topic, value=data)
    producer.flush()

    print(f"Data sent to topic: {topic}")


def produce_all_results(df):

    # Yearly summary
    send_to_kafka(
        "worldcup_yearly_summary",
        yearly_summary(df)
    )

    # Highest attendance
    send_to_kafka(
        "worldcup_highest_attendance",
        highest_attendance(df)
    )

    # Highest average attendance
    send_to_kafka(
        "worldcup_highest_avg_attendance",
        highest_average_attendance(df)
    )

    # Attendance by year
    send_to_kafka(
        "worldcup_attendance_by_year",
        attendance_by_year(df)
    )

    # Average attendance by year
    send_to_kafka(
        "worldcup_avg_attendance_by_year",
        average_attendance_by_year(df)
    )

    # Attendance by host
    send_to_kafka(
        "worldcup_attendance_by_host",
        attendance_by_host(df)
    )

    # Average attendance by host
    send_to_kafka(
        "worldcup_avg_attendance_by_host",
        average_attendance_by_host(df)
    )

    # Best top scorer record
    send_to_kafka(
        "worldcup_best_top_scorer",
        best_top_scorer_record(df)
    )

    # Top scorer goals by year
    send_to_kafka(
        "worldcup_top_scorer_goals",
        top_scorer_goals_by_year(df)
    )

    # Count of top scorers
    send_to_kafka(
        "worldcup_top_scorer_counts",
        country_with_most_top_scorers(df).to_dict()
    )

    # Most frequent top scorer
    send_to_kafka(
        "worldcup_most_frequent_top_scorer",
        {
            "player": most_frequent_top_scorer(df)
        }
    )

    # Host summary
    send_to_kafka(
        "worldcup_host_summary",
        host_summary(df)
    )

    print("All results sent to Kafka successfully.")