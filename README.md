# CRMsys

## System presentation

https://user-images.githubusercontent.com/56121225/168477199-1159bbab-2a36-4157-b4e1-4ec25f078f80.mp4

## Forecast energy module

### On every monday at 5:30 to the clients who are part of at least one "Energy forecast group" email with potential energy production is sent

![image](https://user-images.githubusercontent.com/56121225/168477482-2b9c5345-73a5-4b5d-9038-84f7bc94bd52.png)

### Example of email message

![image](https://user-images.githubusercontent.com/56121225/168477762-ce5107e2-8ad2-4121-85c0-752f94065a76.png)

### How whole procedure looks like?

1. Get contacts who are subscribing energy forecast module.
2. Call Flask API with all necessary informations (photovoltaic plant power, localization of solar plant)
3. Flask API is calling [weather api](https://www.visualcrossing.com/) for weather data
4. Flask API is sending data to tensorflow model which is predicting production
5. Flask API is returning data to Java backend
6. Java backend is sending email to the contact
7. Go to step 2

## Technologies

1. React
2. Spring boot
3. MySql
4. Tensorflow
5. Python Flask

## Authors

### Frontend
[michalcieslik](https://github.com/michalcieslik)
[abcdrefg](https://github.com/abcdrefg)
### Java backend
[marcink2138](https://github.com/marcink2138)
### Python flask backend
[abcdrefg](https://github.com/abcdrefg)
[marcink2138](https://github.com/marcink2138)
