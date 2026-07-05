import google.generativeai as genai

from app.config.settings import settings


class GeminiService:

    def __init__(self):

        genai.configure(
            api_key=settings.GEMINI_API_KEY
        )

        self.model = genai.GenerativeModel(
            "gemini-2.5-flash"
        )

    async def ask(
        self,
        prompt: str
    ) -> str:

        response = self.model.generate_content(
            prompt
        )

        return response.text

    async def ask_with_memory(
        self,
        conversation_history: str
    ) -> str:

        prompt = f"""
You are a FIFA World Cup expert.

You remember the entire conversation.

Answer naturally.

{conversation_history}
"""

        response = self.model.generate_content(
            prompt
        )

        return response.text