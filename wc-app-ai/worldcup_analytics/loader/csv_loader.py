import pandas as pd
import os


def load_summary():

    base_dir = os.path.dirname(os.path.dirname(__file__))

    file_path = os.path.join(base_dir, "data", "FIFA - World Cup Summary.csv")

    df = pd.read_csv(file_path)

    return df