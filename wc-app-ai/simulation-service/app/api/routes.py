from fastapi import APIRouter

from app.elastic.client import es
from app.models.match import MatchRequest
from app.simulator.engine import engine

router = APIRouter()


@router.post("/simulate")
async def simulate_match(
        request: MatchRequest
):
    return await engine.simulate(
        request
    )


@router.get(
    "/matches/{match_id}/events"
)
async def get_match_events(
        match_id: str
):

    result = es.search(
        index="match-events",
        query={
            "term": {
                "match_id.keyword": match_id
            }
        },
        size=1000,
        sort=[
            {
                "minute": {
                    "order": "asc"
                }
            }
        ]
    )

    return [
        hit["_source"]
        for hit in result["hits"]["hits"]
    ]


@router.get(
    "/matches/{match_id}/stats"
)
async def get_match_stats(
        match_id: str
):

    result = es.search(
        index="match-stats",
        query={
            "term": {
                "match_id.keyword": match_id
            }
        },
        size=1
    )

    hits = result["hits"]["hits"]

    if not hits:
        return {}

    return hits[0]["_source"]