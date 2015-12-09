package com.romil93.weatherforecast;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Objects;

/**
 * Created by romil93 on 08/12/15.
 */
public class DetailsActivity extends TabActivity {
    String city;
    String units;
    JSONObject json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        if(bd!=null){
            city = (String) bd.get("city");
            units = (String) bd.get("units");
            try {
                json = new JSONObject((String) bd.get("json"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            TextView heading = (TextView) findViewById(R.id.moreDetailsHeading);
            city = "More Details for " + city;
            heading.setText(city);
        }

        // create the TabHost that will contain the Tabs
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);


        TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabSpec tab2 = tabHost.newTabSpec("Second Tab");

        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        tab1.setIndicator("NEXT 24 HOURS");
        tab1.setContent(new Intent(this,Tab1Activity.class));

        tab2.setIndicator("NEXT 7 DAYS");
        tab2.setContent(new Intent(this,Tab2Activity.class));


        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
    }
}
