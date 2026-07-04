from app.repositories.conversation_repository import (
    ConversationRepository
)

from datetime import datetime



class ConversationService:

    def __init__(self):

        self.repository = (
            ConversationRepository()
        )

    async def get_conversations(
            self,
            user_email: str
    ):
        return await (
            self.repository
            .find_by_user_email(
                user_email
            )
        )

    async def create_conversation(
        self,
        user_email: str
    ):

        return await self.repository.create(
            user_email
        )

    async def update_title(
            self,
            conversation_id: str,
            title: str
    ):
        await self.collection.update_one(

            {
                "_id":
                    ObjectId(
                        conversation_id
                    )
            },

            {
                "$set": {

                    "title": title,

                    "updated_at":
                        datetime.utcnow()

                }
            }

        )