# WeatherForecastAndroid

This is a mobile version of a weather forecast application. Given an address inside of the United States it finds and shows the location and shows the weather conditions at that moment, hourly predictions and for the next week.

Visit http://ww.github.com/romil93/WeatherForecast and from the source file use test.php and follow the instructions of that repo to complete test.php.

The data for the android application is got from the "test.php" file.

Replace "HOSTED_LOCATION" in line 425 of MainActivity.java with the actual hosted location of the file test.php.

## API KEYS

We need two API keys - AERIS Weather API and Facebook Application API.

Proceed to http://www.aerisweather.com/develop/ and create an free tier account. Create and application and it generate the app id and secret, which needs to be updated in "strings.xml".

Proceed to http://developers.facebook.com, choose "New App" for Android. Give it a name and follow the steps provided by facebook. Generating "Key Hashes is very important" follow the tutorial provided by facebook. At the end of the app setup, facebook would have generated a key id and secret.

Remember to make sure to go to the settings of the app and add a contact email before making the app live. Update the facebook app id in "strings.xml" and "AndroidManifest.xml". 

The application  should be up and running on compilation.
