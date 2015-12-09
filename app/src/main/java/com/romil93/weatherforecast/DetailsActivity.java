package com.romil93.weatherforecast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Objects;

/**
 * Created by romil93 on 08/12/15.
 */
public class DetailsActivity extends Activity {
    String city = "";
    String units = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        if(bd != null) {
            city = "More Details for " + bd.get("city");
            units = (String) bd.get("unit");

            TextView moreDetailsText = (TextView) findViewById(R.id.moreDetailsText);
            moreDetailsText.setText(city);

            TextView tempHeading = (TextView) findViewById(R.id.tempHeading);
            String tempHeadingString;
            Log.d("Units", units);
            if(Objects.equals(units, "si")) {
                tempHeadingString = "Temp(" + (char) 0x00B0 + "C)";
                tempHeading.setText(tempHeadingString);
            } else {
                tempHeadingString = "Temp(" + (char) 0x00B0 + "F)";
                tempHeading.setText(tempHeadingString);
            }

            TableLayout tbl = (TableLayout)findViewById(R.id.tableLayout24hours);
            TableRow newRow = new TableRow(this);
            newRow.addView(new TextView(this)); // you would actually want to set properties on this before adding it
            tbl.addView(newRow);
        }
    }
}
