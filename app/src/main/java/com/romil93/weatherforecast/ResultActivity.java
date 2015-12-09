package com.romil93.weatherforecast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;

/**
 * Created by romil93 on 06/12/15.
 */

public class ResultActivity extends Activity {
    JSONObject jsonObj;
    JSONObject daily;
    JSONArray jsonArray;
    ImageView image;
    String city = "";

    String summary_string = "";
    String precipitation = "";
    String chance_of_rain = "";
    String wind_speed = "";
    String dew_point = "";
    String humidity = "";
    String visibility = "";
    String sunrise = "";
    String sunset = "";
    String tempMin = "";
    String tempMax = "";
    String currentTemp = "";

    String units = "";

    TextView summary;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.resource_activity);

        summary = (TextView) findViewById(R.id.summary);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        Log.d("City", (String) bd.get("city"));
        if(bd != null)
        {
            city = (String) bd.get("city");
            units = (String) bd.get("unit");
            try {
                jsonObj= new JSONObject(bd.getString("json"));
                JSONObject currently = jsonObj.getJSONObject("currently");
                summary_string = currently.getString("summary") + " in " + city;
                summary.setText(summary_string);

                TextView tempunit = (TextView) findViewById(R.id.tempunit);
                String tempsummunit = "";
                if(Objects.equals(units, "si")) {
                    tempsummunit  =(char) 0x00B0 + "C";
                    tempunit.setText(tempsummunit);
                } else {
                    tempsummunit = (char) 0x00B0 + "F";
                    tempunit.setText(tempsummunit);
                }

                image = (ImageView) findViewById(R.id.main_image);

                String icon_in_json = currently.getString("icon");
                Log.d("icon", icon_in_json);
                if (Objects.equals(icon_in_json, "clear-day")) {
                    image.setImageResource(R.drawable.clear);
                } else if (Objects.equals(icon_in_json, "rain")) {
                    image.setImageResource(R.drawable.rain);
                } else if (Objects.equals(icon_in_json, "clear-night")) {
                    image.setImageResource(R.drawable.clear_night);
                } else if (Objects.equals(icon_in_json, "sleet")) {
                    image.setImageResource(R.drawable.sleet);
                } else if (Objects.equals(icon_in_json, "wind")) {
                    image.setImageResource(R.drawable.wind);
                } else if (Objects.equals(icon_in_json, "snow")) {
                    image.setImageResource(R.drawable.snow);
                } else if (Objects.equals(icon_in_json, "cloudy")) {
                    image.setImageResource(R.drawable.cloudy);
                } else if (Objects.equals(icon_in_json, "fog")) {
                    image.setImageResource(R.drawable.fog);
                } else if (Objects.equals(icon_in_json, "partly-cloudy-day")) {
                    image.setImageResource(R.drawable.cloud_day);
                } else if (Objects.equals(icon_in_json, "partly-cloudy-night")) {
                    image.setImageResource(R.drawable.cloud_night);
                }


                int precip_intensity = Integer.parseInt(currently.getString("precipIntensity"));
                if (precip_intensity < 0.002) {
                    precipitation = "None";
                } else if (precip_intensity < 0.017) {
                    precipitation = "Very Light";
                } else if (precip_intensity < 0.1) {
                    precipitation = "Light";
                } else if (precip_intensity < 0.4) {
                    precipitation = "Moderate";
                } else if (precip_intensity >= 0.4) {
                    precipitation = "Heavy";
                }

                TextView preci = (TextView) findViewById(R.id.preci);
                preci.setText(precipitation);

                chance_of_rain = currently.getString("precipProbability") + " %";
                TextView rain = (TextView) findViewById(R.id.rain);
                rain.setText(chance_of_rain);

                if(Objects.equals(units, "si")) {
                    wind_speed = currently.getString("windSpeed") + " m/s";
                } else {
                    wind_speed = currently.getString("windSpeed") + " mph";
                }
                TextView speedwind = (TextView) findViewById(R.id.wind);
                speedwind.setText(wind_speed);

                TextView pointdew = (TextView) findViewById(R.id.dew);
                if(Objects.equals(units, "si")) {
                    dew_point = currently.getString("dewPoint") + (char) 0x00B0 + "C";
                } else {
                    dew_point = currently.getString("dewPoint") + (char) 0x00B0 + "F";
                }
                pointdew.setText(dew_point);

                TextView humid = (TextView) findViewById(R.id.humid);
                humidity = (int)(Double.parseDouble(currently.getString("humidity")) * 100) + " %";
                humid.setText(humidity);

                TextView visi = (TextView) findViewById(R.id.visible);
                if(Objects.equals(units, "si")) {
                    visibility = currently.getString("visibility") + " km";
                } else {
                    visibility = currently.getString("visibility") + " mi";
                }

                visi.setText(visibility);

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
                sdf.setTimeZone(TimeZone.getTimeZone(jsonObj.getString("timezone")));

                daily = jsonObj.getJSONObject("daily");
                jsonArray = daily.getJSONArray("data");

                tempMin = jsonArray.getJSONObject(0).getString("temperatureMin");
                tempMax = jsonArray.getJSONObject(0).getString("temperatureMax");
                currentTemp = currently.getString("temperature");

                TextView temp = (TextView) findViewById(R.id.temp);
                TextView tempHighLow = (TextView) findViewById(R.id.highlow);

                tempMin = ((int) Double.parseDouble(tempMin)) + "" + (char) 0x00B0;
                tempMax = ((int) Double.parseDouble(tempMax)) + "" + (char) 0x00B0;
                currentTemp = ((int) Double.parseDouble(currentTemp)) + "";

                Log.d("tempminmax",Double.parseDouble(jsonArray.getJSONObject(0).getString("temperatureMin")) + " " + Double.parseDouble(jsonArray.getJSONObject(0).getString("temperatureMax")));
                temp.setText(currentTemp);
                String highlow = "L: "+ tempMin + " | H: " + tempMax;
                tempHighLow.setText(highlow);


                sunrise = jsonArray.getJSONObject(0).getString("sunriseTime");
                Log.d("sunr", sunrise);
                calendar.setTimeInMillis(Long.parseLong(sunrise) * 1000);

                Log.d("sunrise",sdf.format(calendar.getTime()));
                TextView sunr = (TextView) findViewById(R.id.sunrise);
                sunr.setText(sdf.format(calendar.getTime()));


                sunset = jsonArray.getJSONObject(0).getString("sunsetTime");
                Log.d("sunr", sunset);
                calendar.setTimeInMillis(Long.parseLong(sunset) * 1000);

                Log.d("sunrise",sdf.format(calendar.getTime()));
                TextView suns = (TextView) findViewById(R.id.sunset);
                suns.setText(sdf.format(calendar.getTime()));

                Button moreDetails = (Button) findViewById(R.id.more);

                moreDetails.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        Intent resultIntent =  new Intent(ResultActivity.this, DetailsActivity.class);
                        resultIntent.putExtra("city", city);
                        resultIntent.putExtra("json", jsonObj.toString());
                        resultIntent.putExtra("unit", units);
                        startActivity(resultIntent);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
