from fastapi import APIRouter

from app.services.message_service import (
    MessageService
)

router = APIRouter()

service = MessageService()


@router.get(
    "/conversations/{conversation_id}/messages"
)
async def get_messages(
    conversation_id: str
):

    return await service.get_history(
        conversation_id
    )