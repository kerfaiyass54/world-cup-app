from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from app.api.conversations import router as conversations_router
from app.api.chat import router as chat_router
from app.api.messages import router as messages_router

app = FastAPI(
    title="World Cup AI",
    version="1.0.0"
)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"]
)

app.include_router(
    conversations_router,
    prefix="/api"
)

app.include_router(
    chat_router,
    prefix="/api"
)

app.include_router(
    messages_router,
    prefix="/api"
)

@app.get("/")
async def root():
    return {
        "status": "running",
        "service": "World Cup AI"
    }