import asyncio

from contextlib import (
    asynccontextmanager
)

from fastapi import FastAPI

from fastapi.middleware.cors import (
    CORSMiddleware
)

from app.api.routes import router

from app.kafka.consumer import (
    consume_matches
)

from app.kafka.producer import (
    start_producer,
    stop_producer
)


@asynccontextmanager
async def lifespan(
        app: FastAPI
):

    await start_producer()

    consumer_task = (
        asyncio.create_task(
            consume_matches()
        )
    )

    yield

    consumer_task.cancel()

    await stop_producer()


app = FastAPI(
    title="World Cup Simulator",
    lifespan=lifespan
)

app.add_middleware(
    CORSMiddleware,
    allow_origins=[
        "http://localhost:4200"
    ],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"]
)

app.include_router(router)