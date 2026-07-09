import json

from aiokafka import (
    AIOKafkaProducer
)

from app.config import (
    KAFKA_BOOTSTRAP_SERVERS
)

from app.kafka.topics import (
    SIMULATION_RESULT_TOPIC
)


producer = None


async def start_producer():

    global producer

    producer = AIOKafkaProducer(
        bootstrap_servers=
        KAFKA_BOOTSTRAP_SERVERS
    )

    await producer.start()


async def stop_producer():

    global producer

    if producer:
        await producer.stop()


async def send_result(
        result
):

    if not producer:
        return

    await producer.send_and_wait(
        SIMULATION_RESULT_TOPIC,
        json.dumps(
            result.model_dump()
        ).encode()
    )