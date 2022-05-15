import pandas as pd
import tensorflow as tf
from tensorflow import keras
from keras import layers
import os.path

class EnergyCalculator:

    def __init__(self, df):
        self._dataframe = df
        self.load_model()

    def load_model(self):
        path = os.path.abspath(os.path.dirname(__file__))
        path = os.path.join(path, 'content')
        self._model = tf.keras.models.load_model(path)

    def forecast_energy(self):
        return self._model.predict(self._dataframe)
