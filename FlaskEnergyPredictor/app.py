from flask import Flask
from flask import request

from WeatherAPI import WeatherAPI
from GeoCodeCreator import GeolocationCodeCreator
from EnergyCalculator import EnergyCalculator
import pandas as pd
import numpy as np

app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'Nothing here, go away :)'

@app.route('/api/EnergyProductionForecast', methods = ['GET'])
def get_energy_production_forecast():
    geo_location_create = GeolocationCodeCreator(request.args.get('address'))
    geo_location_create.calulate()
    weather_api = WeatherAPI(geo_location_create.get_lat(), geo_location_create.get_lng())
    weather_data = weather_api.get_weather()
    energy_calculator = EnergyCalculator(weather_data)
    energy_list  = energy_calculator.forecast_energy()
    energy_list = energy_list*float(request.args.get('power'))/8.3
    return np.array2string(energy_list, precision=2, separator=',')

if __name__ == '__main__':
    app.run()
