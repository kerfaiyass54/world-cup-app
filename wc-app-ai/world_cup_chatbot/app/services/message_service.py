from datetime import datetime

from app.repositories.message_repository import (
    MessageRepository
)


class MessageService:

    def __init__(self):

        self.repository = MessageRepository()

    async def save_message(
        self,
        conversation_id: str,
        role: str,
        content: str
    ):

        return await self.repository.create(
            {
                "conversation_id":
                    conversation_id,

                "role":
                    role,

                "content":
                    content,

                "created_at":
                    datetime.utcnow()
            }
        )

    async def get_history(
        self,
        conversation_id: str
    ):

        return await self.repository.get_by_conversation(
            conversation_id
        )