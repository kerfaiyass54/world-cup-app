from fastapi import APIRouter

from app.models.chat_models import (
    ChatRequest,
    ChatResponse
)

from app.services.chat_service import (
    ChatService
)

router = APIRouter()

chat_service = ChatService()


@router.post(
    "/chat",
    response_model=ChatResponse
)
async def chat(
    request: ChatRequest
):

    answer = await chat_service.ask(
        request.conversation_id,
        request.message
    )

    return ChatResponse(
        response=answer
    )