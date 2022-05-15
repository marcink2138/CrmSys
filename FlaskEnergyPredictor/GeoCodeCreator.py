import requests
from geopy.geocoders import Nominatim

class GeolocationCodeCreator:

    def __init__(self, address):
        self._address = address

    def calulate(self):
        locator = Nominatim(user_agent='myGeocoder')
        location = locator.geocode(self._address)
        self._lat = location.latitude
        self._lng = location.longitude

    def get_lat(self):
        return self._lat

    def get_lng(self):
        return self._lng