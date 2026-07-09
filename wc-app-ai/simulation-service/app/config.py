
from dotenv import load_dotenv

load_dotenv()

import os

KAFKA_BOOTSTRAP_SERVERS = os.getenv(
    "KAFKA_BOOTSTRAP_SERVERS",
    "localhost:9092"
)

ELASTIC_URL = os.getenv(
    "ELASTIC_URL",
    "http://localhost:9200"
)