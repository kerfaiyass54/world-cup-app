import json


class PromptBuilder:

    @staticmethod
    def build(
        user_message: str,
        context_data,
        history
    ):

        history_text = ""

        for msg in history[-20:]:

            history_text += (
                f"{msg['role']}: "
                f"{msg['content']}\n"
            )

        context_json = json.dumps(
            context_data,
            indent=2,
            ensure_ascii=False
        )

        return f"""
You are a World Cup AI expert.

Use conversation history.

Conversation:

{history_text}

Dataset Context:

{context_json}

User Question:

{user_message}

Answer naturally and professionally.
"""