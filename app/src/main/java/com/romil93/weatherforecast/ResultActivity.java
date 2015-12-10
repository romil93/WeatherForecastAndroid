package com.romil93.weatherforecast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
    import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;

/**
 * Created by romil93 on 06/12/15.
 */

public class ResultActivity extends FragmentActivity {
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

    LoginButton loginButton;
    TextView info;

    ImageButton fb;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    private final String[] PERMISSIONS = new String[] { "public_profile", "email" };

    Context c;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.resource_activity);


        shareDialog = new ShareDialog(this);
        info = (TextView)findViewById(R.id.info);
        fb = (ImageButton) findViewById(R.id.button);
        c = this;

        fb.setOnClickListener(new View.OnClickListener() {
            String description_fb = null;
            String icon_in_json_fb = null;
            String img_name_fb = null;

            @Override
            public void onClick(View v) {
                img_name_fb = "http://weather-forecast-csci571.elasticbeanstalk.com/";
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    Intent intent1 = getIntent();
                    Bundle bd1 = intent1.getExtras();

                    try {
                        jsonObj = new JSONObject((String) bd1.get("json"));
                        JSONObject current = jsonObj.getJSONObject("currently");
                        description_fb = current.getString("summary") + ", " + (int) Double.parseDouble(current.getString("temperature"));
                        city = (String) bd1.get("city");
                        icon_in_json_fb = current.getString("icon");

                        units = (String) bd1.get("unit");
                    }catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (Objects.equals(icon_in_json_fb, "clear-day")) {
                        img_name_fb = img_name_fb + "images/clear.png";
                    }
                    else if (Objects.equals(icon_in_json_fb, "rain")) {
                        img_name_fb = img_name_fb + "images/rain.png";
                    }
                    else if (Objects.equals(icon_in_json_fb, "clear-night")) {
                        img_name_fb = img_name_fb + "images/clear_night.png";
                    }
                    else if (Objects.equals(icon_in_json_fb, "sleet")) {
                        img_name_fb = img_name_fb + "images/sleet.png";
                    }
                    else if (Objects.equals(icon_in_json_fb, "wind")) {
                        img_name_fb = img_name_fb + "images/wind.png";
                    }
                    else if (Objects.equals(icon_in_json_fb, "snow")) {
                        img_name_fb = img_name_fb + "images/snow.png";
                    }
                    else if (Objects.equals(icon_in_json_fb, "cloudy")) {
                        img_name_fb = img_name_fb + "images/cloudy.png";
                    }
                    else if (Objects.equals(icon_in_json_fb, "fog")) {
                        img_name_fb = img_name_fb + "images/fog.png";
                    }
                    else if (Objects.equals(icon_in_json_fb, "partly-cloudy-day")) {
                        img_name_fb = img_name_fb + "images/cloud_day.png";
                    }
                    else if (Objects.equals(icon_in_json_fb, "partly-cloudy-night")) {
                        img_name_fb = img_name_fb + "images/cloud_night.png";
                    }

                    if(Objects.equals(units, "si")) {
                        description_fb  = description_fb + (char) 0x00B0 + "C";
                    } else {
                        description_fb = description_fb + (char) 0x00B0 + "F";
                    }

                    Log.d("Image", img_name_fb);

                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Current Weather in " + city)
                            .setContentDescription(description_fb)
                            .setImageUrl(Uri.parse(img_name_fb))
                            .setContentUrl(Uri.parse("http://forecast.io"))
                            .build();

                    shareDialog = new ShareDialog((Activity) c);

                    shareDialog.show(linkContent);

                    // this part is optional
                    shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                        @Override
                        public void onSuccess(Sharer.Result result) {
                            Toast.makeText(c, "Facebook Post Successful", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancel() {
                            Toast.makeText(c, "Post Unsuccessful", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(FacebookException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });




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

                Button Map = (Button) findViewById(R.id.map);

                Map.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Intent resultIntent =  new Intent(ResultActivity.this, MapActivity.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
