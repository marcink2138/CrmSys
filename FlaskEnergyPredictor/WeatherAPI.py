import requests
import pandas as pd
from io import StringIO

class WeatherAPI:

    def __init__(self, latitude, longitude):
        self._latitude = latitude
        self._longitude = longitude
        self._key = 'UF9F5VQL4M4UC67TL6HEKEHZD'

    def get_weather(self):
        url = 'https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/weatherdata/forecast'
        parameters = {
            'location': str(self._latitude) + ',' + str(self._longitude),
            'forecastDays': '7',
            'aggregateHours': '24',
            'key': self._key
        }
        response = requests.get(url, params=parameters)
        return self.parse_data(response)

    def parse_data(self, response):
        dataset = pd.read_csv(StringIO(response.text), sep=',', skipinitialspace=True, na_values='?')
        df = pd.DataFrame()
        df.insert(0, 'tempmax', (dataset.get('Maximum Temperature')-32) / 1.8)
        df.insert(1, 'tempmin', (dataset.get('Minimum Temperature')-32) / 1.8)
        df.insert(2, 'temp', (dataset.get('Temperature')-32) / 1.8)
        df.insert(3, 'humidity', dataset.get('Relative Humidity'))
        df.insert(4, 'cloudcover', dataset.get('Cloud Cover'))
        return df
