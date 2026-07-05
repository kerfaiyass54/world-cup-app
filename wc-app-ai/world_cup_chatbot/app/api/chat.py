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

    result = await chat_service.send_message(
        request.conversation_id,
        request.message
    )

    return ChatResponse(
        response=result["response"]
    )