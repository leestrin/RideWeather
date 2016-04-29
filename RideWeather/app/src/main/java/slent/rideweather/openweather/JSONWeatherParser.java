package slent.rideweather.openweather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import slent.rideweather.model.Location;
import slent.rideweather.model.Weather;

/**
 * Created by Simon on 2015-07-14.
 */
public class JSONWeatherParser {

    //////////////////////////////Getting Current Weather INFO//////////////////////////////
    public static Weather getWeather(String data) throws JSONException {
        Weather weather = new Weather();
        System.out.println("Data [" + data + "]");
        // We create out JSONObject from the data
        JSONObject jObj = new JSONObject(data);

        // We start extracting the info
        Location loc = new Location();

        JSONObject coordObj = getObject("coord", jObj);
        loc.setLatitude(getFloat("lat", coordObj));
        loc.setLongitude(getFloat("lon", coordObj));

        JSONObject sysObj = getObject("sys", jObj);
        loc.setCountry(getString("country", sysObj));
        loc.setSunrise(getInt("sunrise", sysObj));
        loc.setSunset(getInt("sunset", sysObj));
        loc.setCity(getString("name", jObj));
        weather.location = loc;

        // We get weather info (This is an array)
        JSONArray jArr = jObj.getJSONArray("weather");

        // We use only the first value
        JSONObject JSONWeather = jArr.getJSONObject(0);
        weather.currentCondition.setWeatherId(getInt("id", JSONWeather));
        weather.currentCondition.setDescr(getString("description", JSONWeather));
        weather.currentCondition.setCondition(getString("main", JSONWeather));
        weather.currentCondition.setIcon(getString("icon", JSONWeather));

        JSONObject mainObj = getObject("main", jObj);
        weather.currentCondition.setHumidity(getInt("humidity", mainObj));
        weather.currentCondition.setPressure(getInt("pressure", mainObj));
        weather.temperature.setMaxTemp(getFloat("temp_max", mainObj));
        weather.temperature.setMinTemp(getFloat("temp_min", mainObj));
        weather.temperature.setTemp(getFloat("temp", mainObj));

        // Wind
        JSONObject wObj = getObject("wind", jObj);
        weather.wind.setSpeed(getFloat("speed", wObj));
        weather.wind.setDeg(getFloat("deg", wObj));

        // Clouds
        JSONObject cObj = getObject("clouds", jObj);
        weather.clouds.setPerc(getInt("all", cObj));

        // We download the icon to show


        return weather;
    }
    //////////////////////////////Getting Current Weather DONE//////////////////////////////

    //////////////////////////////Getting First(3HR) Weather INFO//////////////////////////////
    public static Weather getFutureFirstWeather(String data) throws JSONException {
        Weather weatherFirstHour = new Weather();
        System.out.println("Data [" + data + "]");
        // We create out JSONObject from the data
        JSONObject jObj = new JSONObject(data);

        // We start extracting the info
        //Location loc = new Location();

        //JSONObject coordObj = getObject("coord", jObj);
        //loc.setLatitude(getFloat("lat", coordObj));
        //loc.setLongitude(getFloat("lon", coordObj));

        //JSONObject sysObj = getObject("sys", jObj);
        //loc.setCountry(getString("country", sysObj));
        //loc.setSunrise(getInt("sunrise", sysObj));
        //loc.setSunset(getInt("sunset", sysObj));
        //loc.setCity(getString("name", jObj));
        //weather.location = loc;

        // We get weather info (This is an array)
        JSONArray jArr = jObj.getJSONArray("list");

        // We use only the first value
        JSONObject JSONWeather = jArr.getJSONObject(0);
        JSONObject wObj = JSONWeather.getJSONObject("wind");
        weatherFirstHour.wind.setDeg(getFloat("deg", wObj));
        weatherFirstHour.wind.setSpeed(getFloat("speed", wObj));

        // Get main Info
        JSONObject mainObj = JSONWeather.getJSONObject("main");
        weatherFirstHour.currentCondition.setHumidity(getInt("humidity", mainObj));
        weatherFirstHour.temperature.setMaxTemp(getFloat("temp_max", mainObj));
        weatherFirstHour.temperature.setMinTemp(getFloat("temp_min", mainObj));
        weatherFirstHour.temperature.setTemp(getFloat("temp", mainObj));

        JSONObject cObj = JSONWeather.getJSONObject("clouds");
        weatherFirstHour.clouds.setPerc(getInt("all", cObj));

        //get weather info
        JSONObject weatherObj = JSONWeather.getJSONArray("weather").getJSONObject(0);
        weatherFirstHour.currentCondition.setWeatherId(getInt("id", weatherObj));
        weatherFirstHour.currentCondition.setCondition(getString("main", weatherObj));
        weatherFirstHour.currentCondition.setDescr(getString("description", weatherObj));

        // We download the icon to show


        return weatherFirstHour;
    }
    //////////////////////////////Getting First(3HR) Weather DONE//////////////////////////////

    //////////////////////////////Getting Second(6HR) Weather INFO//////////////////////////////
    public static Weather getFutureSecondWeather(String data) throws JSONException {
        Weather weatherSecondtHour = new Weather();
        System.out.println("Data [" + data + "]");
        // We create out JSONObject from the data
        JSONObject jObj = new JSONObject(data);

        // We start extracting the info
        //Location loc = new Location();

        //JSONObject coordObj = getObject("coord", jObj);
        //loc.setLatitude(getFloat("lat", coordObj));
        //loc.setLongitude(getFloat("lon", coordObj));

        //JSONObject sysObj = getObject("sys", jObj);
        //loc.setCountry(getString("country", sysObj));
        //loc.setSunrise(getInt("sunrise", sysObj));
        //loc.setSunset(getInt("sunset", sysObj));
        //loc.setCity(getString("name", jObj));
        //weather.location = loc;

        // We get weather info (This is an array)
        JSONArray jArr = jObj.getJSONArray("list");

        // We use only the first value
        JSONObject JSONWeather = jArr.getJSONObject(1);
        JSONObject wObj = JSONWeather.getJSONObject("wind");
        weatherSecondtHour.wind.setDeg(getFloat("deg", wObj));
        weatherSecondtHour.wind.setSpeed(getFloat("speed", wObj));

        // Get main Info
        JSONObject mainObj = JSONWeather.getJSONObject("main");
        weatherSecondtHour.currentCondition.setHumidity(getInt("humidity", mainObj));
        weatherSecondtHour.temperature.setMaxTemp(getFloat("temp_max", mainObj));
        weatherSecondtHour.temperature.setMinTemp(getFloat("temp_min", mainObj));
        weatherSecondtHour.temperature.setTemp(getFloat("temp", mainObj));

        JSONObject cObj = JSONWeather.getJSONObject("clouds");
        weatherSecondtHour.clouds.setPerc(getInt("all", cObj));

        //get weather info
        JSONObject weatherObj = JSONWeather.getJSONArray("weather").getJSONObject(0);
        weatherSecondtHour.currentCondition.setWeatherId(getInt("id", weatherObj));
        weatherSecondtHour.currentCondition.setCondition(getString("main", weatherObj));
        weatherSecondtHour.currentCondition.setDescr(getString("description", weatherObj));

        // We download the icon to show


        return weatherSecondtHour;
    }
    //////////////////////////////Getting Second(6HR) Weather DONE//////////////////////////////

    //////////////////////////////Getting Future(12HR) Weather INFO//////////////////////////////
    public static Weather getFutureThirdWeather(String data) throws JSONException {
        Weather weatherThirdHour = new Weather();
        System.out.println("Data [" + data + "]");
        // We create out JSONObject from the data
        JSONObject jObj = new JSONObject(data);

        // We start extracting the info
        //Location loc = new Location();

        //JSONObject coordObj = getObject("coord", jObj);
        //loc.setLatitude(getFloat("lat", coordObj));
        //loc.setLongitude(getFloat("lon", coordObj));

        //JSONObject sysObj = getObject("sys", jObj);
        //loc.setCountry(getString("country", sysObj));
        //loc.setSunrise(getInt("sunrise", sysObj));
        //loc.setSunset(getInt("sunset", sysObj));
        //loc.setCity(getString("name", jObj));
        //weather.location = loc;

        // We get weather info (This is an array)
        JSONArray jArr = jObj.getJSONArray("list");

        // We use only the first value
        JSONObject JSONWeather = jArr.getJSONObject(3);
        JSONObject wObj = JSONWeather.getJSONObject("wind");
        weatherThirdHour.wind.setDeg(getFloat("deg", wObj));
        weatherThirdHour.wind.setSpeed(getFloat("speed", wObj));

        // Get main Info
        JSONObject mainObj = JSONWeather.getJSONObject("main");
        weatherThirdHour.currentCondition.setHumidity(getInt("humidity", mainObj));
        weatherThirdHour.temperature.setMaxTemp(getFloat("temp_max", mainObj));
        weatherThirdHour.temperature.setMinTemp(getFloat("temp_min", mainObj));
        weatherThirdHour.temperature.setTemp(getFloat("temp", mainObj));

        JSONObject cObj = JSONWeather.getJSONObject("clouds");
        weatherThirdHour.clouds.setPerc(getInt("all", cObj));

        //get weather info
        JSONObject weatherObj = JSONWeather.getJSONArray("weather").getJSONObject(0);
        weatherThirdHour.currentCondition.setWeatherId(getInt("id", weatherObj));
        weatherThirdHour.currentCondition.setCondition(getString("main", weatherObj));
        weatherThirdHour.currentCondition.setDescr(getString("description", weatherObj));

        // We download the icon to show


        return weatherThirdHour;
    }
    //////////////////////////////Getting Future(12HR) Weather DONE//////////////////////////////


    private static JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

}