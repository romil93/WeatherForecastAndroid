package com.romil93.weatherforecast;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
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
 * Created by romil93 on 08/12/15.
 */
public class Tab2Activity  extends Activity
{
    String city;
    String units;
    String minMax;

    JSONObject json;
    JSONObject daily;
    JSONArray dailyArray;

    TextView date;
    TextView tempMinMax;
    ImageView image;

    public String time_timezone(long timeinmillis, String timezone) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM d");
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));

        calendar.setTimeInMillis(timeinmillis * 1000);

        return sdf.format(calendar.getTime());
    }

    public void set_image(String icon_in_json, ImageView cell) {
        if (Objects.equals(icon_in_json, "clear-day")) {
            cell.setImageResource(R.drawable.clear);
        } else if (Objects.equals(icon_in_json, "rain")) {
            cell.setImageResource(R.drawable.rain);
        } else if (Objects.equals(icon_in_json, "clear-night")) {
            cell.setImageResource(R.drawable.clear_night);
        } else if (Objects.equals(icon_in_json, "sleet")) {
            cell.setImageResource(R.drawable.sleet);
        } else if (Objects.equals(icon_in_json, "wind")) {
            cell.setImageResource(R.drawable.wind);
        } else if (Objects.equals(icon_in_json, "snow")) {
            cell.setImageResource(R.drawable.snow);
        } else if (Objects.equals(icon_in_json, "cloudy")) {
            cell.setImageResource(R.drawable.cloudy);
        } else if (Objects.equals(icon_in_json, "fog")) {
            cell.setImageResource(R.drawable.fog);
        } else if (Objects.equals(icon_in_json, "partly-cloudy-day")) {
            cell.setImageResource(R.drawable.cloud_day);
        } else if (Objects.equals(icon_in_json, "partly-cloudy-night")) {
            cell.setImageResource(R.drawable.cloud_night);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab2layout);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        if(bd != null) {
            city = (String) bd.get("city");
            units = (String) bd.get("unit");
            try {
                json = new JSONObject((String) bd.get("json"));
                daily = json.getJSONObject("daily");
                dailyArray = daily.getJSONArray("data");


                //Day1
                date = (TextView) findViewById(R.id.day1date);
                image = (ImageView) findViewById(R.id.day1image);
                tempMinMax = (TextView) findViewById(R.id.day1minmax);

                date.setText(time_timezone(Long.parseLong(dailyArray.getJSONObject(0).getString("time")),json.getString("timezone")));
                set_image(dailyArray.getJSONObject(0).getString("icon"), image);
                if(Objects.equals(units, "si")) {
                    minMax  = "Min: " + (int)Double.parseDouble(dailyArray.getJSONObject(0).getString("temperatureMin")) + (char) 0x00B0 + "C" + " | Max: " + (int)Double.parseDouble(dailyArray.getJSONObject(0).getString("temperatureMax")) + (char) 0x00B0 + "C";
                } else {
                    minMax  = "Min: " + (int)Double.parseDouble(dailyArray.getJSONObject(0).getString("temperatureMin")) + (char) 0x00B0 + "F" + " | Max: " + (int)Double.parseDouble(dailyArray.getJSONObject(0).getString("temperatureMax")) + (char) 0x00B0 + "F";
                }
                tempMinMax.setText(minMax);

                //Day2
                date = (TextView) findViewById(R.id.day2date);
                image = (ImageView) findViewById(R.id.day2image);
                tempMinMax = (TextView) findViewById(R.id.day2minmax);

                date.setText(time_timezone(Long.parseLong(dailyArray.getJSONObject(1).getString("time")),json.getString("timezone")));
                set_image(dailyArray.getJSONObject(1).getString("icon"), image);
                if(Objects.equals(units, "si")) {
                    minMax  = "Min: " + (int)Double.parseDouble(dailyArray.getJSONObject(1).getString("temperatureMin")) + (char) 0x00B0 + "C" + " | Max: " + (int)Double.parseDouble(dailyArray.getJSONObject(1).getString("temperatureMax")) + (char) 0x00B0 + "C";
                } else {
                    minMax  = "Min: " + (int)Double.parseDouble(dailyArray.getJSONObject(1).getString("temperatureMin")) + (char) 0x00B0 + "F" + " | Max: " + (int)Double.parseDouble(dailyArray.getJSONObject(1).getString("temperatureMax")) + (char) 0x00B0 + "F";
                }
                tempMinMax.setText(minMax);

                //Day3
                date = (TextView) findViewById(R.id.day3date);
                image = (ImageView) findViewById(R.id.day3image);
                tempMinMax = (TextView) findViewById(R.id.day3minmax);

                date.setText(time_timezone(Long.parseLong(dailyArray.getJSONObject(2).getString("time")),json.getString("timezone")));
                set_image(dailyArray.getJSONObject(2).getString("icon"), image);
                if(Objects.equals(units, "si")) {
                    minMax  = "Min: " + (int)Double.parseDouble(dailyArray.getJSONObject(2).getString("temperatureMin")) + (char) 0x00B0 + "C" + " | Max: " + (int)Double.parseDouble(dailyArray.getJSONObject(2).getString("temperatureMax")) + (char) 0x00B0 + "C";
                } else {
                    minMax  = "Min: " + (int)Double.parseDouble(dailyArray.getJSONObject(2).getString("temperatureMin")) + (char) 0x00B0 + "F" + " | Max: " + (int)Double.parseDouble(dailyArray.getJSONObject(2).getString("temperatureMax")) + (char) 0x00B0 + "F";
                }
                tempMinMax.setText(minMax);

                //Day4
                date = (TextView) findViewById(R.id.day4date);
                image = (ImageView) findViewById(R.id.day4image);
                tempMinMax = (TextView) findViewById(R.id.day4minmax);

                date.setText(time_timezone(Long.parseLong(dailyArray.getJSONObject(3).getString("time")),json.getString("timezone")));
                set_image(dailyArray.getJSONObject(3).getString("icon"), image);
                if(Objects.equals(units, "si")) {
                    minMax  = "Min: " + (int)Double.parseDouble(dailyArray.getJSONObject(3).getString("temperatureMin")) + (char) 0x00B0 + "C" + " | Max: " + (int)Double.parseDouble(dailyArray.getJSONObject(3).getString("temperatureMax")) + (char) 0x00B0 + "C";
                } else {
                    minMax  = "Min: " + (int)Double.parseDouble(dailyArray.getJSONObject(3).getString("temperatureMin")) + (char) 0x00B0 + "F" + " | Max: " + (int)Double.parseDouble(dailyArray.getJSONObject(3).getString("temperatureMax")) + (char) 0x00B0 + "F";
                }
                tempMinMax.setText(minMax);

                //Day5
                date = (TextView) findViewById(R.id.day5date);
                image = (ImageView) findViewById(R.id.day5image);
                tempMinMax = (TextView) findViewById(R.id.day5minmax);

                date.setText(time_timezone(Long.parseLong(dailyArray.getJSONObject(4).getString("time")),json.getString("timezone")));
                set_image(dailyArray.getJSONObject(4).getString("icon"), image);
                if(Objects.equals(units, "si")) {
                    minMax  = "Min: " + (int)Double.parseDouble(dailyArray.getJSONObject(4).getString("temperatureMin")) + (char) 0x00B0 + "C" + " | Max: " + (int)Double.parseDouble(dailyArray.getJSONObject(4).getString("temperatureMax")) + (char) 0x00B0 + "C";
                } else {
                    minMax  = "Min: " + (int)Double.parseDouble(dailyArray.getJSONObject(4).getString("temperatureMin")) + (char) 0x00B0 + "F" + " | Max: " + (int)Double.parseDouble(dailyArray.getJSONObject(4).getString("temperatureMax")) + (char) 0x00B0 + "F";
                }
                tempMinMax.setText(minMax);

                //Day6
                date = (TextView) findViewById(R.id.day6date);
                image = (ImageView) findViewById(R.id.day6image);
                tempMinMax = (TextView) findViewById(R.id.day6minmax);

                date.setText(time_timezone(Long.parseLong(dailyArray.getJSONObject(5).getString("time")),json.getString("timezone")));
                set_image(dailyArray.getJSONObject(5).getString("icon"), image);
                if(Objects.equals(units, "si")) {
                    minMax  = "Min: " + (int)Double.parseDouble(dailyArray.getJSONObject(5).getString("temperatureMin")) + (char) 0x00B0 + "C" + " | Max: " + (int)Double.parseDouble(dailyArray.getJSONObject(5).getString("temperatureMax")) + (char) 0x00B0 + "C";
                } else {
                    minMax  = "Min: " + (int)Double.parseDouble(dailyArray.getJSONObject(5).getString("temperatureMin")) + (char) 0x00B0 + "F" + " | Max: " + (int)Double.parseDouble(dailyArray.getJSONObject(5).getString("temperatureMax")) + (char) 0x00B0 + "F";
                }
                tempMinMax.setText(minMax);

                //Day7
                date = (TextView) findViewById(R.id.day7date);
                image = (ImageView) findViewById(R.id.day7image);
                tempMinMax = (TextView) findViewById(R.id.day7minmax);

                date.setText(time_timezone(Long.parseLong(dailyArray.getJSONObject(6).getString("time")),json.getString("timezone")));
                set_image(dailyArray.getJSONObject(6).getString("icon"), image);
                if(Objects.equals(units, "si")) {
                    minMax  = "Min: " + (int)Double.parseDouble(dailyArray.getJSONObject(6).getString("temperatureMin")) + (char) 0x00B0 + "C" + " | Max: " + (int)Double.parseDouble(dailyArray.getJSONObject(6).getString("temperatureMax")) + (char) 0x00B0 + "C";
                } else {
                    minMax  = "Min: " + (int)Double.parseDouble(dailyArray.getJSONObject(6).getString("temperatureMin")) + (char) 0x00B0 + "F" + " | Max: " + (int)Double.parseDouble(dailyArray.getJSONObject(6).getString("temperatureMax")) + (char) 0x00B0 + "F";
                }
                tempMinMax.setText(minMax);

            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }
}