from dotenv import load_dotenv
import os

load_dotenv()


class Settings:

    MONGO_URI = os.getenv("MONGO_URI")

    DATABASE_NAME = os.getenv("DATABASE_NAME")

    GEMINI_API_KEY = os.getenv("GEMINI_API_KEY")


settings = Settings()