from src.loader import load_data
from src.cleaner import clean_data
from src.preprocessor import preprocess_data
from src.statistics import (
    calculate_team_statistics
)
from src.producer import send_to_kafka


DATA_PATH = (
    "data/matches_1930_2022.csv"
)


def main():

    print("Loading data...")
    df = load_data(DATA_PATH)

    print("Cleaning data...")
    df = clean_data(df)

    print("Preprocessing data...")
    df = preprocess_data(df)

    print("Calculating statistics...")
    stats = calculate_team_statistics(df)


    print(
        "Statistics saved "
        "to output/team_statistics.csv"
    )

    print("Sending to Kafka...")
    send_to_kafka(stats)

    print("Done")


if __name__ == "__main__":
    main()