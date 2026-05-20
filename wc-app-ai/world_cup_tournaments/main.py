import pandas as pd

from pipeline.kafka_producer import produce_all_results


# Load CSV
df = pd.read_csv("data/world_cup.csv")


# Send all analytics to Kafka
produce_all_results(df)