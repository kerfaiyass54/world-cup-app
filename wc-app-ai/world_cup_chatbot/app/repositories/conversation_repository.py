from datetime import datetime

from app.database.mongodb import db

from bson import ObjectId


from app.utils.mongo_mapper import (
    conversation_to_dict
)


class ConversationRepository:

    collection = db.conversations

    async def create(
        self,
        user_email: str
    ):

        document = {

            "user_email": user_email,

            "title": "New Conversation",

            "created_at": datetime.utcnow(),

            "updated_at": datetime.utcnow()

        }

        result = await self.collection.insert_one(
            document
        )

        saved_document = (
            await self.collection.find_one(
                {
                    "_id":
                        result.inserted_id
                }
            )
        )

        return conversation_to_dict(
            saved_document
        )

    async def find_by_user_email(
            self,
            user_email: str
    ):
        cursor = (
            self.collection
            .find(
                {
                    "user_email": user_email
                }
            )
            .sort(
                "updated_at",
                -1
            )
        )

        conversations = []

        async for document in cursor:
            conversations.append(
                conversation_to_dict(
                    document
                )
            )

        return conversations

    async def delete(
            self,
            conversation_id: str
    ):
        result = await self.collection.delete_one(
            {
                "_id": ObjectId(
                    conversation_id
                )
            }
        )

        return {
            "deleted":
                result.deleted_count > 0
        }