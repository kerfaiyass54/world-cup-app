def conversation_to_dict(
    document
):

    return {

        "id": str(
            document["_id"]
        ),

        "user_email":
            document["user_email"],

        "title":
            document["title"],

        "created_at":
            document["created_at"],

        "updated_at":
            document["updated_at"]

    }