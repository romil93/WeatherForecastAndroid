package com.romil93.weatherforecast;

/**
 * Created by romil93 on 18/11/15.
 */

import android.os.Bundle;
import android.app.Activity;

public class AboutActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.about_activity);
    }
}