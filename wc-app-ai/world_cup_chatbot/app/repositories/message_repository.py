from bson import ObjectId

from app.database.mongodb import db
from bson import ObjectId


class MessageRepository:

    def __init__(self):

        self.collection = db.messages

    async def create(
        self,
        message_data: dict
    ):

        result = await self.collection.insert_one(
            message_data
        )

        message_data["_id"] = str(
            result.inserted_id
        )

        return message_data

    async def get_by_conversation(
        self,
        conversation_id: str
    ):

        cursor = self.collection.find(
            {
                "conversation_id":
                    conversation_id
            }
        ).sort(
            "created_at",
            1
        )

        return await cursor.to_list(
            length=1000
        )

    async def find_by_conversation(
            self,
            conversation_id: str
    ):
        cursor = self.collection.find(
            {
                "conversation_id":
                    conversation_id
            }
        ).sort(
            "created_at",
            1
        )

        return await cursor.to_list(
            length=1000
        )