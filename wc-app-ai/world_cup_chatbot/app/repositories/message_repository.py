from app.database.mongodb import db


class MessageRepository:

    async def create(
        self,
        data: dict
    ):
        result = await (
            db.messages.insert_one(data)
        )

        return str(
            result.inserted_id
        )

    async def find_by_conversation(
        self,
        conversation_id: str
    ):

        cursor = (
            db.messages
            .find(
                {
                    "conversation_id":
                    conversation_id
                }
            )
            .sort(
                "created_at",
                1
            )
        )

        messages = []

        async for document in cursor:

            document["_id"] = str(
                document["_id"]
            )

            messages.append(
                document
            )

        return messages