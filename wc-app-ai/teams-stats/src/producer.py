import json

from kafka import KafkaProducer

from config.kafka_config import (
    BOOTSTRAP_SERVERS,
    TOPIC_NAME
)


def send_to_kafka(stats_df):

    producer = KafkaProducer(
        bootstrap_servers=BOOTSTRAP_SERVERS,
        value_serializer=lambda v:
        json.dumps(v).encode("utf-8")
    )

    for _, row in stats_df.iterrows():

        producer.send(
            TOPIC_NAME,
            row.to_dict()
        )

    producer.flush()

    print(
        f"Sent {len(stats_df)} teams "
        f"to Kafka topic '{TOPIC_NAME}'"
    )