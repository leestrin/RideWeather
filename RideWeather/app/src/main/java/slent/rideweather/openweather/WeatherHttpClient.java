package slent.rideweather.openweather;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Simon on 2015-07-15.
 */
public class WeatherHttpClient {

    //private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?lat=37.5667&lon=126.9667";
    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";
    ////////////////////////////////////Fixed to Hanam///////////////////////////////
    //TODO Fix this for my location
    //private static String BASE_FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?lat=37.5667&lon=126.9667";
    private static String BASE_FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&q=";

    private static String BASE_FIRSTSECONDTHIRD_URL = "http://api.openweathermap.org/data/2.5/forecast?";
    private static String APPID = "14c80c9396d2e0c87c1cbe46e17b77a9";

    public String getWeatherData(String location, String lang) {
        HttpURLConnection con = null;
        InputStream is = null;

        try {
            String url = BASE_URL + location;
            //String url = BASE_URL;
            if (lang != null)
                url = url + "&lang=" + lang + "&APPID=" + APPID;

            con = (HttpURLConnection) (new URL(url)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while (  (line = br.readLine()) != null )
                buffer.append(line + "\r\n");

            is.close();
            con.disconnect();

            return buffer.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }

    public String getWeatherFirstSecondThirdData(String location, String lang) {
        HttpURLConnection con = null;
        InputStream is = null;

        try {
            String url = BASE_FIRSTSECONDTHIRD_URL + location;
            //String url = BASE_FIRSTSECONDTHIRD_URL;
            //if (lang != null)
              //  url = url + "&lang=" + lang;
            url = url + "&cnt=4" + "&APPID=" + APPID;

            con = (HttpURLConnection) (new URL(url)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while (  (line = br.readLine()) != null )
                buffer.append(line + "\r\n");

            is.close();
            con.disconnect();

            return buffer.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }

/* DON'T NEED for my project
    public byte[] getImage(String code) {
        HttpURLConnection con = null ;
        InputStream is = null;
        try {
            con = (HttpURLConnection) ( new URL(IMG_URL + code)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            is = con.getInputStream();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while ( is.read(buffer) != -1)
                baos.write(buffer);

            return baos.toByteArray();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }
*/
}