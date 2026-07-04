from datetime import datetime

from app.repositories.message_repository import (
    MessageRepository
)

from app.services.conversation_memory import (
    ConversationMemory
)

from app.services.orchestrator_instance import (
    orchestrator
)


class ChatService:

    def __init__(self):

        self.message_repository = (
            MessageRepository()
        )

    async def send_message(
        self,
        conversation_id: str,
        user_message: str
    ):

        previous_messages = (
            await self.message_repository
            .find_by_conversation(
                conversation_id
            )
        )

        memory = (
            ConversationMemory
            .build_prompt(
                previous_messages,
                user_message
            )
        )

        ai_response = (
            await orchestrator.process(
                user_message,
                memory
            )
        )

        await self.message_repository.create(
            {
                "conversation_id":
                    conversation_id,
                "role":
                    "user",
                "content":
                    user_message,
                "created_at":
                    datetime.utcnow()
            }
        )

        await self.message_repository.create(
            {
                "conversation_id":
                    conversation_id,
                "role":
                    "assistant",
                "content":
                    ai_response,
                "created_at":
                    datetime.utcnow()
            }
        )

        return {
            "response":
                ai_response
        }