package com.romil93.weatherforecast;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
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

public class Tab1Activity  extends Activity
{
    String units;
    String city;

    JSONObject json;
    JSONObject hourly;
    JSONArray hourlyArray;

    public String time_timezone(long timeinmillis, String timezone) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));

        calendar.setTimeInMillis(timeinmillis * 1000);

        return sdf.format(calendar.getTime());
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1layout);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        if(bd != null) {
            city = (String) bd.get("city");
            units = (String) bd.get("unit");

            try {
                json = new JSONObject((String) bd.get("json"));
                hourly = json.getJSONObject("hourly");
                hourlyArray = hourly.getJSONArray("data");

                String timezone = json.getString("timezone");

                TableLayout tbl = (TableLayout)findViewById(R.id.hours24);

                for (int row = 1; row <= 24; row++) {
                    TableRow rowgroup = new TableRow(this);
                    if(row%2 == 1) {
                        rowgroup.setBackgroundColor(Color.parseColor("#8b8383"));
                    }

                    JSONObject data = hourlyArray.getJSONObject(row);
                    for (int column = 0; column < 3; column++) {
                        if (column == 0) {
                            long gettime = data.getLong("time");
                            String time = time_timezone(gettime, timezone);
                            TextView cell = new TextView(this);
                            cell.setWidth(150);
                            cell.setHeight(150);

                            cell.setText(time);
                            cell.setTextColor((Color.parseColor("#000000")));
                            cell.setVisibility(View.VISIBLE);
                            cell.setGravity(Gravity.CENTER);
                            cell.setTextSize(16);
                            rowgroup.addView(cell);
                        } else if (column == 1) {
                            ImageView cell = new ImageView(this);
                            String icon_in_json = data.getString("icon");

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

                            cell.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                            cell.setVisibility(View.VISIBLE);
                            rowgroup.addView(cell);

                            cell.requestLayout();
                            cell.getLayoutParams().height = 150;
                            cell.getLayoutParams().width = 150;
                        } else {
                            TextView cell = new TextView(this);
                            Integer temperature = data.getInt("temperature");
                            cell.setWidth(100);
                            cell.setHeight(100);
                            cell.setTextSize(16);
                            String tempUnit;
                            if(Objects.equals(units, "si")) {
                                tempUnit  =(char) 0x00B0 + "C";
                            } else {
                                tempUnit = (char) 0x00B0 + "F";
                            }

                            cell.setText(temperature + tempUnit);
                            cell.setTextColor((Color.parseColor("#000000")));
                            cell.setVisibility(View.VISIBLE);

                            cell.setGravity(Gravity.CENTER);

                            rowgroup.addView(cell);
                        }

                    }
                    tbl.addView(rowgroup);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}