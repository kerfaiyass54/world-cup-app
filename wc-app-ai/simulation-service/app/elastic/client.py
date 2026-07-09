from elasticsearch import Elasticsearch

from app.config import ELASTIC_URL


es = Elasticsearch(
    hosts=[ELASTIC_URL]
)


async def save_event(event):

    es.index(
        index="match-events",
        document=event.model_dump()
    )


async def save_match_stats(stats):

    es.index(
        index="match-stats",
        document=stats.model_dump()
    )