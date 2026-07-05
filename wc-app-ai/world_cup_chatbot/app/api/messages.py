from fastapi import APIRouter

from app.services.message_service import (
    MessageService
)

router = APIRouter()

service = MessageService()


@router.get(
    "/messages/{conversation_id}"
)
async def get_messages(
    conversation_id: str
):

    return await (
        service.get_history(
            conversation_id
        )
    )