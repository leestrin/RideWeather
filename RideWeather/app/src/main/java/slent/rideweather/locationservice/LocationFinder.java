package slent.rideweather.locationservice;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Simon on 2015-07-15.
 */
public class LocationFinder {

    Double latitude;
    Double longitude;

    String location;

    public void setLocation(String tempLoc) {
        location = tempLoc;
    }
    public void setLat(String tempLoc) {
        latitude = getLat(getLocationInfo(tempLoc));
    }
    public void setLon(String tempLoc) {
        longitude = getlon(getLocationInfo(tempLoc));
    }

    public Double optLat() {
        return latitude;
    }
    public Double optLon() {
        return longitude;
    }


    @SuppressWarnings("deprecation")
    public static JSONObject getLocationInfo(String address) {
        StringBuilder stringBuilder = new StringBuilder();
        try {

            address = address.replaceAll(" ", "%20");

            HttpPost httppost = new HttpPost("http://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false");
            HttpClient client = new DefaultHttpClient();
            HttpResponse response;
            stringBuilder = new StringBuilder();


            response = client.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }

    public static Double getLat(JSONObject jsonObject) {
        Double tempLatitude = null;
        try {
            tempLatitude = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tempLatitude;
    }

    public static Double getlon(JSONObject jsonObject) {
        Double tempLongitute = null;
        try {
            tempLongitute = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                  .getJSONObject("geometry").getJSONObject("location")
                .getDouble("lng");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tempLongitute;
    }

}