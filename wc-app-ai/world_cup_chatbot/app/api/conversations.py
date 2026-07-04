from fastapi import APIRouter

from app.models.conversation import (
    ConversationCreate
)

from app.services.conversation_service import (
    ConversationService
)

router = APIRouter(
    prefix="/conversations",
    tags=["Conversations"]
)

service = ConversationService()


@router.post("")
async def create_conversation(
    request: ConversationCreate
):

    return await service.create_conversation(
        request.user_email
    )

@router.get(
    "/{user_email}"
)
async def get_conversations(
    user_email: str
):

    return await (
        service.get_conversations(
            user_email
        )
    )