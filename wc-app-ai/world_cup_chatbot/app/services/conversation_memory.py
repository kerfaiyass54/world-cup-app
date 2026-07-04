class ConversationMemory:

    @staticmethod
    def build_prompt(
        messages: list,
        current_question: str
    ) -> str:

        history = ""

        for message in messages:

            role = message["role"]

            content = message["content"]

            if role == "user":

                history += (
                    f"User: {content}\n"
                )

            else:

                history += (
                    f"Assistant: {content}\n"
                )

        history += (
            f"\nUser: {current_question}"
        )

        return history