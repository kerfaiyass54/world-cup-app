from app.repositories.message_repository import (
    MessageRepository
)


class MessageService:

    def __init__(self):

        self.repository = (
            MessageRepository()
        )

    async def get_history(
        self,
        conversation_id: str
    ):

        messages = await (
            self.repository
            .find_by_conversation(
                conversation_id
            )
        )

        return messages