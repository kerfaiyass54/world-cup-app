import logging

from app.kafka.consumer import consumer_service

logger = logging.getLogger(__name__)


async def start_worker():

    logger.info(
        "Simulation Worker Started"
    )

    await consumer_service.start()