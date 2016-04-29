package slent.rideweather;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import slent.rideweather.locationservice.LocationFinder;
import slent.rideweather.openweather.JSONWeatherParser;


//mport slent.rideweather.model.Location;
import slent.rideweather.model.Weather;
import slent.rideweather.openweather.WeatherHttpClient;

//import com.flurry.android.FlurryAgent;

/**
 * Created by Simon on 2015-07-02.
 * All rights reserved for Simon J. Lee of SLent.
 */


public class WeatherActivity extends ActionBarActivity {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;


    //My Own!!
    private TextView windMsTextView;
    //private EditText inputCityEditText;
    private ImageView windDirectionImageView;
    private TextView sunRiseTextView;
    private TextView sunSetTextView;
    private TextView rideHintTextView;

    //private ProgressDialog dialog;

    //private String locationFrmApiString;

    private Exception error;


    private TextView firstHourWindTextView;
    private TextView secondHourWindTextView;
    private TextView thirdHourWindTextView;
    private TextView firstHourTempTextView;
    private TextView secondHourTempTextView;
    private TextView thirdHourTempTextView;
    private TextView firstHourHumidTextView;
    private TextView secondHourHumidTextView;
    private TextView thirdHourHumidTextView;
    private ImageView firstHourWindImageView;
    private ImageView secondHourWindImageView;
    private ImageView thirdHourWindImageView;
    private ImageView firstHourCondImageView;
    private ImageView secondHourCondImageView;
    private ImageView thirdHourCondImageView;
    private TextView humidityTextView;
    private TextView firstHourTextView;
    private TextView secondHourTextView;
    private TextView thirdHourTextView;

    //private MenuItem mSearchItem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);


        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        //mSearchItem = menu.getItem(0);
        searchView.setQueryHint(getString(R.string.my_search_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                //mSearchItem.collapseActionView();

                firstHourTextView = (TextView) findViewById(R.id.firstHourTextView);
                secondHourTextView = (TextView) findViewById(R.id.secondHourTextView);
                thirdHourTextView = (TextView) findViewById(R.id.thirdHourTextView);

                weatherIconImageView = (ImageView) findViewById(R.id.weatherIconImageView);
                temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
                conditionTextView = (TextView) findViewById(R.id.conditionTextView);
                locationTextView = (TextView) findViewById(R.id.locationTextView);
                // inputCityEditText = (EditText) findViewById(R.id.inputCityEditText);
                windDirectionImageView = (ImageView) findViewById(R.id.windDirectionImageView);

                //my own!!
                windMsTextView = (TextView) findViewById(R.id.windMsTextView);

                sunRiseTextView = (TextView) findViewById(R.id.sunRiseTextView);
                sunSetTextView = (TextView) findViewById(R.id.sunSetTextView);

                rideHintTextView = (TextView) findViewById(R.id.rideHintTextView);

                firstHourWindTextView = (TextView) findViewById(R.id.firstHourWindSpeedTextView);
                secondHourWindTextView = (TextView) findViewById(R.id.secondHourWindSpeedTextView);
                thirdHourWindTextView = (TextView) findViewById(R.id.thirdHourWindSpeedTextView);
                firstHourTempTextView = (TextView) findViewById(R.id.firstHourTempTextView);
                secondHourTempTextView = (TextView) findViewById(R.id.secondHourTempTextView);
                thirdHourTempTextView = (TextView) findViewById(R.id.thirdHourTempTextView);
                firstHourWindImageView = (ImageView) findViewById(R.id.firstHourWindImageView);
                secondHourWindImageView = (ImageView) findViewById(R.id.secondHourWindImageView);
                thirdHourWindImageView = (ImageView) findViewById(R.id.thirdHourWindImageView);
                firstHourCondImageView = (ImageView) findViewById(R.id.firstHourCondImageView);
                secondHourCondImageView = (ImageView) findViewById(R.id.secondHourCondImageView);
                thirdHourCondImageView = (ImageView) findViewById(R.id.thirdHourCondImageView);
                firstHourHumidTextView = (TextView) findViewById(R.id.firstHourHumidTextView);
                secondHourHumidTextView = (TextView) findViewById(R.id.secondHourHumidTextView);
                thirdHourHumidTextView = (TextView) findViewById(R.id.thirdHourHumidTextView);
                humidityTextView = (TextView) findViewById(R.id.humidityTextView);

                firstHourWindImageView = (ImageView) findViewById(R.id.firstHourWindImageView);
                secondHourWindImageView = (ImageView) findViewById(R.id.secondHourWindImageView);
                thirdHourWindImageView = (ImageView) findViewById(R.id.thirdHourWindImageView);


                String tempLocation;
                tempLocation = query;


                try {
                    LatLng coordinate = getLocationFromAddress(tempLocation);
                    Double tempLat = coordinate.latitude;
                    Double tempLon = coordinate.longitude;

                    System.out.println("jinwook: " + coordinate.toString());


                    //lat=37.5667&lon=126.9667
                    String locationLatLon = "lat=" + String.valueOf(tempLat) + "&lon=" + String.valueOf(tempLon);


                    JSONWeatherTask task = new JSONWeatherTask();
                    task.execute(new String[]{locationLatLon, ""});

                    JSONWeatherFirstTask taskFirst = new JSONWeatherFirstTask();
                    taskFirst.execute(new String[]{locationLatLon, ""});

                    JSONWeatherSecondTask taskSecond = new JSONWeatherSecondTask();
                    taskSecond.execute(new String[]{locationLatLon, ""});

                    JSONWeatherThirdTask taskThird = new JSONWeatherThirdTask();
                    taskThird.execute(new String[]{locationLatLon, ""});

                } catch (Exception e) {
                    error = new LocationWeatherException("No weather information found for " + query);
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return true;
        // return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //case R.id.action_search:
            //  openSearch();
            // return true;
            case R.id.refresh:

                Toast.makeText(getApplicationContext(), getString(R.string.findingYourLocation), Toast.LENGTH_LONG).show();

                //final Geocoder geocoder = new Geocoder(this);

                //manages location
                LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


                LocationListener listener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                        try {
                            String locationLatLon;
                            //lat=37.5667&lon=126.9667
                            locationLatLon = "lat=" + String.valueOf(location.getLatitude()) + "&lon=" + String.valueOf(location.getLongitude());

                            JSONWeatherTask task = new JSONWeatherTask();
                            task.execute(new String[]{locationLatLon, ""});

                            JSONWeatherFirstTask taskFirst = new JSONWeatherFirstTask();
                            taskFirst.execute(new String[]{locationLatLon, ""});

                            JSONWeatherSecondTask taskSecond = new JSONWeatherSecondTask();
                            taskSecond.execute(new String[]{locationLatLon, ""});

                            JSONWeatherThirdTask taskThird = new JSONWeatherThirdTask();
                            taskThird.execute(new String[]{locationLatLon, ""});

                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                };

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 5000, listener);
                ///////////////////////////////////////////////////////////////////////

                //JSONWeatherTask task = new JSONWeatherTask();
                //task.execute(new String[]{city, lang});


                weatherIconImageView = (ImageView) findViewById(R.id.weatherIconImageView);
                temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
                conditionTextView = (TextView) findViewById(R.id.conditionTextView);
                locationTextView = (TextView) findViewById(R.id.locationTextView);
                // inputCityEditText = (EditText) findViewById(R.id.inputCityEditText);
                windDirectionImageView = (ImageView) findViewById(R.id.windDirectionImageView);

                //my own!!
                windMsTextView = (TextView) findViewById(R.id.windMsTextView);
                sunRiseTextView = (TextView) findViewById(R.id.sunRiseTextView);
                sunSetTextView = (TextView) findViewById(R.id.sunSetTextView);
                rideHintTextView = (TextView) findViewById(R.id.rideHintTextView);


                firstHourTextView = (TextView) findViewById(R.id.firstHourTextView);
                secondHourTextView = (TextView) findViewById(R.id.secondHourTextView);
                thirdHourTextView = (TextView) findViewById(R.id.thirdHourTextView);

                firstHourWindTextView = (TextView) findViewById(R.id.firstHourWindSpeedTextView);
                secondHourWindTextView = (TextView) findViewById(R.id.secondHourWindSpeedTextView);
                thirdHourWindTextView = (TextView) findViewById(R.id.thirdHourWindSpeedTextView);
                firstHourTempTextView = (TextView) findViewById(R.id.firstHourTempTextView);
                secondHourTempTextView = (TextView) findViewById(R.id.secondHourTempTextView);
                thirdHourTempTextView = (TextView) findViewById(R.id.thirdHourTempTextView);
                firstHourWindImageView = (ImageView) findViewById(R.id.firstHourWindImageView);
                secondHourWindImageView = (ImageView) findViewById(R.id.secondHourWindImageView);
                thirdHourWindImageView = (ImageView) findViewById(R.id.thirdHourWindImageView);
                firstHourCondImageView = (ImageView) findViewById(R.id.firstHourCondImageView);
                secondHourCondImageView = (ImageView) findViewById(R.id.secondHourCondImageView);
                thirdHourCondImageView = (ImageView) findViewById(R.id.thirdHourCondImageView);
                firstHourHumidTextView = (TextView) findViewById(R.id.firstHourHumidTextView);
                secondHourHumidTextView = (TextView) findViewById(R.id.secondHourHumidTextView);
                thirdHourHumidTextView = (TextView) findViewById(R.id.thirdHourHumidTextView);
                humidityTextView = (TextView) findViewById(R.id.humidityTextView);

                firstHourWindImageView = (ImageView) findViewById(R.id.firstHourWindImageView);
                secondHourWindImageView = (ImageView) findViewById(R.id.secondHourWindImageView);
                thirdHourWindImageView = (ImageView) findViewById(R.id.thirdHourWindImageView);
                return true;

            case R.id.switch_fahrenheit:
                SavePrefs("mode", "fahrenheit");
                Toast.makeText(getApplicationContext(), R.string.modeChanged, Toast.LENGTH_LONG).show();

                return true;

            case R.id.switch_celsius:
                SavePrefs("mode", "celsius");
                Toast.makeText(getApplicationContext(), R.string.modeChanged, Toast.LENGTH_LONG).show();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        //Disable keyboard popup when start
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // configure Flurry
        //FlurryAgent.setLogEnabled(false);

        // init Flurry
        //FlurryAgent.init(this, "836CWD9JJ4Z3M8JZMBD4");


        //String city = "Foo, Boo";
        //String lang = ""; //Not do anything
        ///////////////////////////////////////////////////////////////////////
        //final Geocoder geocoder = new Geocoder(this);

        //manages location
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                try {
                    String locationLatLon;
                    //lat=37.5667&lon=126.9667
                    locationLatLon = "lat=" + String.valueOf(location.getLatitude()) + "&lon=" + String.valueOf(location.getLongitude());

                    JSONWeatherTask task = new JSONWeatherTask();
                    task.execute(new String[]{locationLatLon, ""});

                    JSONWeatherFirstTask taskFirst = new JSONWeatherFirstTask();
                    taskFirst.execute(new String[]{locationLatLon, ""});

                    JSONWeatherSecondTask taskSecond = new JSONWeatherSecondTask();
                    taskSecond.execute(new String[]{locationLatLon, ""});

                    JSONWeatherThirdTask taskThird = new JSONWeatherThirdTask();
                    taskThird.execute(new String[]{locationLatLon, ""});

                    //JSONForecastWeatherTask task1 = new JSONForecastWeatherTask();
                    //task1.execute(new String[]{"london, uk","en", "2"});

                } catch (Exception e) {
                }


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        Toast.makeText(getApplicationContext(), getString(R.string.findingYourLocation), Toast.LENGTH_LONG).show();
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 5000, listener);
        ///////////////////////////////////////////////////////////////////////

        //JSONWeatherTask task = new JSONWeatherTask();
        //task.execute(new String[]{city, lang});


        weatherIconImageView = (ImageView) findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView) findViewById(R.id.conditionTextView);
        locationTextView = (TextView) findViewById(R.id.locationTextView);
        //inputCityEditText = (EditText) findViewById(R.id.inputCityEditText);
        windDirectionImageView = (ImageView) findViewById(R.id.windDirectionImageView);

        //my own!!
        windMsTextView = (TextView) findViewById(R.id.windMsTextView);
        sunRiseTextView = (TextView) findViewById(R.id.sunRiseTextView);
        sunSetTextView = (TextView) findViewById(R.id.sunSetTextView);
        rideHintTextView = (TextView) findViewById(R.id.rideHintTextView);

        firstHourTextView = (TextView) findViewById(R.id.firstHourTextView);
        secondHourTextView = (TextView) findViewById(R.id.secondHourTextView);
        thirdHourTextView = (TextView) findViewById(R.id.thirdHourTextView);

        firstHourWindTextView = (TextView) findViewById(R.id.firstHourWindSpeedTextView);
        secondHourWindTextView = (TextView) findViewById(R.id.secondHourWindSpeedTextView);
        thirdHourWindTextView = (TextView) findViewById(R.id.thirdHourWindSpeedTextView);
        firstHourTempTextView = (TextView) findViewById(R.id.firstHourTempTextView);
        secondHourTempTextView = (TextView) findViewById(R.id.secondHourTempTextView);
        thirdHourTempTextView = (TextView) findViewById(R.id.thirdHourTempTextView);
        firstHourWindImageView = (ImageView) findViewById(R.id.firstHourWindImageView);
        secondHourWindImageView = (ImageView) findViewById(R.id.secondHourWindImageView);
        thirdHourWindImageView = (ImageView) findViewById(R.id.thirdHourWindImageView);
        firstHourCondImageView = (ImageView) findViewById(R.id.firstHourCondImageView);
        secondHourCondImageView = (ImageView) findViewById(R.id.secondHourCondImageView);
        thirdHourCondImageView = (ImageView) findViewById(R.id.thirdHourCondImageView);
        firstHourHumidTextView = (TextView) findViewById(R.id.firstHourHumidTextView);
        secondHourHumidTextView = (TextView) findViewById(R.id.secondHourHumidTextView);
        thirdHourHumidTextView = (TextView) findViewById(R.id.thirdHourHumidTextView);
        humidityTextView = (TextView) findViewById(R.id.humidityTextView);

        firstHourWindImageView = (ImageView) findViewById(R.id.firstHourWindImageView);
        secondHourWindImageView = (ImageView) findViewById(R.id.secondHourWindImageView);
        thirdHourWindImageView = (ImageView) findViewById(R.id.thirdHourWindImageView);


//TODO FIGURE SOMETHING OUT RATHER THAN DIALOG
        //dialog = new ProgressDialog(this);
        //dialog.setMessage(getResources().getString(R.string.findingYourLocation));
        //dialog.show();

    }

    /*
    //My Own!!
    public void buttonOnClick(View v) {
        //do something when sendCity is clicked
        // Button button = (Button) v;
        weatherIconImageView = (ImageView) findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView) findViewById(R.id.conditionTextView);
        locationTextView = (TextView) findViewById(R.id.locationTextView);
        inputCityEditText = (EditText) findViewById(R.id.inputCityEditText);
        windDirectionImageView = (ImageView) findViewById(R.id.windDirectionImageView);
        //my own!!
        updateStatus = (TextView) findViewById(R.id.updateStatus);
        windMsTextView = (TextView) findViewById(R.id.windMsTextView);
        sunRiseTextView = (TextView) findViewById(R.id.sunRiseTextView);
        sunSetTextView = (TextView) findViewById(R.id.sunSetTextView);
        rideHintTextView = (TextView) findViewById(R.id.rideHintTextView);
        firstHourWindTextView = (TextView) findViewById(R.id.firstHourWindSpeedTextView);
        secondHourWindTextView = (TextView) findViewById(R.id.secondHourWindSpeedTextView);
        thirdHourWindTextView = (TextView) findViewById(R.id.thirdHourWindSpeedTextView);
        firstHourTempTextView = (TextView) findViewById(R.id.firstHourTempTextView);
        secondHourTempTextView = (TextView) findViewById(R.id.secondHourTempTextView);
        thirdHourTempTextView = (TextView) findViewById(R.id.thirdHourTempTextView);
        firstHourWindImageView = (ImageView) findViewById(R.id.firstHourWindImageView);
        secondHourWindImageView = (ImageView) findViewById(R.id.secondHourWindImageView);
        thirdHourWindImageView = (ImageView) findViewById(R.id.thirdHourWindImageView);
        firstHourCondImageView = (ImageView) findViewById(R.id.firstHourCondImageView);
        secondHourCondImageView = (ImageView) findViewById(R.id.secondHourCondImageView);
        thirdHourCondImageView = (ImageView) findViewById(R.id.thirdHourCondImageView);
        firstHourHumidTextView =(TextView) findViewById(R.id.firstHourHumidTextView);
        secondHourHumidTextView =(TextView) findViewById(R.id.secondHourHumidTextView);
        thirdHourHumidTextView =(TextView) findViewById(R.id.thirdHourHumidTextView);
        humidityTextView = (TextView) findViewById(R.id.humidityTextView);
        String tempLocation;
        tempLocation = inputCityEditText.getText().toString();
        try {
            LatLng coordinate = getLocationFromAddress(tempLocation);
            Double tempLat = coordinate.latitude;
            Double tempLon = coordinate.longitude;
            System.out.println("jinwook: " + coordinate.toString());
            //lat=37.5667&lon=126.9667
            String locationLatLon = "lat=" + String.valueOf(tempLat) + "&lon=" + String.valueOf(tempLon);
            JSONWeatherTask task = new JSONWeatherTask();
            task.execute(new String[]{locationLatLon, ""});
            JSONWeatherFirstTask taskFirst = new JSONWeatherFirstTask();
            taskFirst.execute(new String[]{locationLatLon, ""});
            JSONWeatherSecondTask taskSecond = new JSONWeatherSecondTask();
            taskSecond.execute(new String[]{locationLatLon, ""});
            JSONWeatherThirdTask taskThird = new JSONWeatherThirdTask();
            taskThird.execute(new String[]{locationLatLon, ""});

        } catch (Exception e) {
            error = new LocationWeatherException("No weather information found for " + inputCityEditText.getText().toString());
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
        }
        //hide keyboard!!
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputCityEditText.getWindowToken(), 0);
        String inputText;
    } //end of onButtonClick
*/
    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = ((new WeatherHttpClient()).getWeatherData(params[0], params[1]));

            try {
                weather = JSONWeatherParser.getWeather(data);
                System.out.println("Weather [" + weather + "]");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            String tempMode = LoadPrefs();
            if (tempMode.equals("celsius")) {
                temperatureTextView.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "°C");
                windMsTextView.setText("" + weather.wind.getSpeed() + " m/s");

            } else {
                temperatureTextView.setText("" + Math.round((weather.temperature.getTemp() - 273.15) * 1.8 + 32) + "°F");
                windMsTextView.setText("" + Math.round(weather.wind.getSpeed() * 2.237) + " Mph");
            }

            conditionTextView.setText(weather.currentCondition.getCondition());

            int tempWindDirection = Math.round(weather.wind.getDeg());
            int windResourceId = getResources().getIdentifier("drawable/over_" + windDirectionSimplified(tempWindDirection), null, getPackageName());
            @SuppressWarnings("deprecation")
            Drawable windDirectionDrawable = getResources().getDrawable(windResourceId);
            windDirectionImageView.setImageDrawable(windDirectionDrawable);
            rideHintTextView.setText(hintGenerator(Math.round(weather.wind.getDeg())));

            //int tempWindSpeed = Math.round(weather.wind.getSpeed());


            //Time
            long sunriseTime = weather.location.getSunrise() * (long) 1000;
            Date sunriseDate = new Date(sunriseTime);
            String sunrise = new SimpleDateFormat("ahh:mm").format(sunriseDate);
            sunRiseTextView.setText(getString(R.string.sunrise) + sunrise);
            long sunsetTime = weather.location.getSunset() * (long) 1000;
            Date sunsetDate = new Date(sunsetTime);
            String sunset = new SimpleDateFormat("ahh:mm").format(sunsetDate);
            sunSetTextView.setText(getString(R.string.sunset) + sunset);

            int tempWeatherId = weather.currentCondition.getWeatherId();
            int weatherRescouceId = getResources().getIdentifier("drawable/weather_" + tempWeatherId, null, getPackageName());
            @SuppressWarnings("deprecation")
            Drawable weatherIdDrawable = getResources().getDrawable(weatherRescouceId);
            weatherIconImageView.setImageDrawable(weatherIdDrawable);

            locationTextView.setText(weather.location.getCity() + ", " + weather.location.getCountry());
            humidityTextView.setText(getString(R.string.humidity) + " " + Math.round(weather.currentCondition.getHumidity()) + "%");


        }
    }

    private class JSONWeatherFirstTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            Weather weatherFirst = new Weather();
            String data = ((new WeatherHttpClient()).getWeatherFirstSecondThirdData(params[0], params[1]));

            try {
                weatherFirst = JSONWeatherParser.getFutureFirstWeather(data);
                System.out.println("Weather [" + weatherFirst + "]");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weatherFirst;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            String tempMode = LoadPrefs();
            if (tempMode.equals("celsius")) {
                firstHourTempTextView.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "°C");
                firstHourWindTextView.setText("" + weather.wind.getSpeed() + " m/s");

            } else {
                firstHourTempTextView.setText("" + Math.round((weather.temperature.getTemp() - 273.15) * 1.8 + 32) + "°F");
                firstHourWindTextView.setText("" + Math.round(weather.wind.getSpeed() * 2.237) + " Mph");
            }
            firstHourHumidTextView.setText(Math.round(weather.currentCondition.getHumidity()) + "%");

            int tempWeatherId = weather.currentCondition.getWeatherId();
            int weatherRescouceId = getResources().getIdentifier("drawable/weather_" + tempWeatherId, null, getPackageName());
            @SuppressWarnings("deprecation")
            Drawable weatherIdDrawable = getResources().getDrawable(weatherRescouceId);
            firstHourCondImageView.setImageDrawable(weatherIdDrawable);

            firstHourTextView.setText(getString(R.string.firstHour));

            int tempWindDirection = Math.round(weather.wind.getDeg());
            int windResourceId = getResources().getIdentifier("drawable/small_over_" + windDirectionSimplified(tempWindDirection), null, getPackageName());
            @SuppressWarnings("deprecation")
            Drawable windDirectionDrawable = getResources().getDrawable(windResourceId);
            firstHourWindImageView.setImageDrawable(windDirectionDrawable);


        }
    }

    private class JSONWeatherSecondTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            Weather weatherSecond = new Weather();
            String data = ((new WeatherHttpClient()).getWeatherFirstSecondThirdData(params[0], params[1]));

            try {
                weatherSecond = JSONWeatherParser.getFutureSecondWeather(data);
                System.out.println("Weather [" + weatherSecond + "]");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weatherSecond;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);


            String tempMode = LoadPrefs();
            if (tempMode.equals("celsius")) {
                secondHourTempTextView.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "°C");
                secondHourWindTextView.setText("" + weather.wind.getSpeed() + " m/s");

            } else {
                secondHourTempTextView.setText("" + Math.round((weather.temperature.getTemp() - 273.15) * 1.8 + 32) + "°F");
                secondHourWindTextView.setText("" + Math.round(weather.wind.getSpeed() * 2.237) + " Mph");
            }
            secondHourHumidTextView.setText(Math.round(weather.currentCondition.getHumidity()) + "%");

            int tempWeatherId = weather.currentCondition.getWeatherId();
            int weatherRescouceId = getResources().getIdentifier("drawable/weather_" + tempWeatherId, null, getPackageName());
            @SuppressWarnings("deprecation")
            Drawable weatherIdDrawable = getResources().getDrawable(weatherRescouceId);
            secondHourCondImageView.setImageDrawable(weatherIdDrawable);

            secondHourTextView.setText(getString(R.string.secondHour));

            int tempWindDirection = Math.round(weather.wind.getDeg());
            int windResourceId = getResources().getIdentifier("drawable/small_over_" + windDirectionSimplified(tempWindDirection), null, getPackageName());
            @SuppressWarnings("deprecation")
            Drawable windDirectionDrawable = getResources().getDrawable(windResourceId);
            secondHourWindImageView.setImageDrawable(windDirectionDrawable);

        }
    }

    private class JSONWeatherThirdTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            Weather weatherThird = new Weather();
            String data = ((new WeatherHttpClient()).getWeatherFirstSecondThirdData(params[0], params[1]));

            try {
                weatherThird = JSONWeatherParser.getFutureThirdWeather(data);
                System.out.println("Weather [" + weatherThird + "]");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weatherThird;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);


            String tempMode = LoadPrefs();
            if (tempMode.equals("celsius")) {
                thirdHourTempTextView.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "°C");
                thirdHourWindTextView.setText("" + weather.wind.getSpeed() + " m/s");

            } else {
                thirdHourTempTextView.setText("" + Math.round((weather.temperature.getTemp() - 273.15) * 1.8 + 32) + "°F");
                thirdHourWindTextView.setText("" + Math.round(weather.wind.getSpeed() * 2.237) + " Mph");
            }
            thirdHourHumidTextView.setText(Math.round(weather.currentCondition.getHumidity()) + "%");

            int tempWeatherId = weather.currentCondition.getWeatherId();
            int weatherRescouceId = getResources().getIdentifier("drawable/weather_" + tempWeatherId, null, getPackageName());
            @SuppressWarnings("deprecation")
            Drawable weatherIdDrawable = getResources().getDrawable(weatherRescouceId);
            thirdHourCondImageView.setImageDrawable(weatherIdDrawable);

            thirdHourTextView.setText(getString(R.string.thirdHour));

            int tempWindDirection = Math.round(weather.wind.getDeg());
            int windResourceId = getResources().getIdentifier("drawable/small_over_" + windDirectionSimplified(tempWindDirection), null, getPackageName());
            @SuppressWarnings("deprecation")
            Drawable windDirectionDrawable = getResources().getDrawable(windResourceId);
            thirdHourWindImageView.setImageDrawable(windDirectionDrawable);


        }
    }


    public int windDirectionSimplified(int direction) {
        if (direction > 315) {
            //ride ro south
            return 315;
        } else if (direction > 300) {
            //ride to south east
            return 300;
        } else if (direction > 255) {
            //ride to east
            return 255;
        } else if (direction > 210) {
            //ride to north east
            return 210;
        } else if (direction > 165) {
            //ride to north
            return 165;
        } else if (direction > 120) {
            //ride to north west
            return 120;
        } else if (direction > 75) {
            //ride to west
            return 75;
        } else if (direction > 30) {
            //ride to south west
            return 30;
        } else {
            return 315;
        }
    }

    public String hintGenerator(int direction) {
        if (direction > 315) {
            //ride ro south
            return getString(R.string.suggestSouth);
        } else if (direction > 300) {
            //ride to south east
            return getString(R.string.suggestSouthEast);
        } else if (direction > 255) {
            //ride to east
            return getString(R.string.suggestEast);
        } else if (direction > 210) {
            //ride to north east
            return getString(R.string.suggestNorthEast);
        } else if (direction > 165) {
            //ride to north
            return getString(R.string.suggestNorth);
        } else if (direction > 120) {
            //ride to north west
            return getString(R.string.suggestNorthWest);
        } else if (direction > 75) {
            //ride to west
            return getString(R.string.suggestWest);
        } else if (direction > 30) {
            //ride to south west
            return getString(R.string.suggestSouthWest);
        } else {
            return getString(R.string.suggestSouth);
        }
    }

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    public class LocationWeatherException extends Exception {
        public LocationWeatherException(String detailMessage) {
            super(detailMessage);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //FlurryAgent.onStartSession(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //FlurryAgent.onEndSession(this);
    }

    private String LoadPrefs() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String modeValue = sharedPrefs.getString("mode", "celsius");
        return modeValue;

    }

    private void SavePrefs(String key, String value) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putString(key, value);
        edit.commit();

    }


}//end of WeatherActivity