import json

from aiokafka import (
    AIOKafkaConsumer
)

from app.config import (
    KAFKA_BOOTSTRAP_SERVERS
)

from app.models.match import (
    MatchRequest
)

from app.simulator.engine import (
    engine
)

from app.kafka.topics import (
    SIMULATION_REQUEST_TOPIC
)


consumer = None


async def consume_matches():

    global consumer

    consumer = AIOKafkaConsumer(
        SIMULATION_REQUEST_TOPIC,
        bootstrap_servers=
        KAFKA_BOOTSTRAP_SERVERS,
        group_id=
        "simulation-service"
    )

    await consumer.start()

    try:

        async for msg in consumer:

            data = json.loads(
                msg.value.decode()
            )

            request = MatchRequest(
                **data
            )

            await engine.simulate(
                request
            )

    finally:

        await consumer.stop()